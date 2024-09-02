package co.luisfbejaranob.backend.users.app.entities.enumerations;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role
{
    ROLE_ADMINISTRATOR(List.of(
            Operation.READ_ALL_USERS,
            Operation.READ_USER,
            Operation.READ_PROFILE,
            Operation.CREATE_USER,
            Operation.UPDATE_USER,
            Operation.DISABLE_USER
    )),
    ROLE_HUMAN_RESOURCES(List.of(
            Operation.READ_ALL_USERS,
            Operation.READ_USER,
            Operation.READ_PROFILE,
            Operation.UPDATE_USER
    )),
    ROLE_USER(List.of(
            Operation.READ_PROFILE
    )),
    ;

    private List<Operation> operations;

    Role(List<Operation> operations)
    {
        this.operations = operations;
    }

    public void setOperations(List<Operation> operations)
    {
        this.operations = operations;
    }
}
