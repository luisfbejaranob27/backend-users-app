package co.luisfbejaranob.backend.users.app.security.authorization;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.entities.Operation;
import co.luisfbejaranob.backend.users.app.security.entities.GrantedPermission;
import co.luisfbejaranob.backend.users.app.security.services.OperationService;
import co.luisfbejaranob.backend.users.app.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext>
{
    private final OperationService operationService;

    private final UserService userService;

    public CustomAuthorizationManager(OperationService operationService, UserService userServiceImpl)
    {
        this.operationService = operationService;
        this.userService = userServiceImpl;
    }

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authentication,
            RequestAuthorizationContext requestContext
    )
    {
        HttpServletRequest request = requestContext.getRequest();
        String httpMethod = request.getMethod();
        String url = extractUrl(request);

        boolean isPublic = isPublic(url , httpMethod);

        if(isPublic)
        {
            return new AuthorizationDecision(true);
        }

        boolean isGranted = isGranted(url , httpMethod , authentication.get());

        return new AuthorizationDecision(isGranted);
    }

    private String extractUrl(HttpServletRequest request)
    {
        String contextPath = request.getContextPath();

        return request.getRequestURI().replace(contextPath, "");
    }

    private boolean isPublic(String url, String httpMethod)
    {
        List<Operation> publicAccessEndpoints = operationService.findAllByPermitAllTrue();

        return validatePathAndMethodAndOperation(url, httpMethod, publicAccessEndpoints);
    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication)
    {
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }

        List<Operation> operations = obtainOperations(authentication);

        return validatePathAndMethodAndOperation(url, httpMethod, operations);
    }

    private List<Operation> obtainOperations(Authentication authentication)
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) authentication;

        String username = (String) authenticationToken.getPrincipal();
        User user = userService.findByUsername(username);

        return user.getRole().getPermissions().stream()
                .map(GrantedPermission::getOperation)
                .collect(Collectors.toList());
    }

    private boolean validatePathAndMethodAndOperation(
            String url,
            String httpMethod,
            List<Operation> operations
    )
    {
        return operations.stream().anyMatch(operation ->
        {
            String basePath = operation.getModule().getBasePath();

            Pattern patternPath = Pattern.compile(basePath.concat(operation.getPath()));
            Pattern patternHttpMethod = Pattern.compile(httpMethod);
            Matcher matcherPath = patternPath.matcher(url);
            Matcher matcherHttpMethod = patternHttpMethod.matcher(httpMethod);

            return matcherPath.matches() && matcherHttpMethod.matches();
        });
    }
}
