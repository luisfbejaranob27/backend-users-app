package co.luisfbejaranob.backend.users.app.entities.enumerations;

import lombok.Getter;

import java.util.List;

@Getter
public enum Role
{
    ADMINISTRATOR(List.of(
            Operation.READ_ALL_USERS,
            Operation.READ_USER_BY_ID,
            Operation.READ_USER_BY_NAME,
            Operation.READ_PROFILE,
            Operation.EXIST_USER,
            Operation.CREATE_USER,
            Operation.UPDATE_USER,
            Operation.DELETE_USER
    )),
    HUMAN_RESOURCES(List.of(
            Operation.READ_ALL_USERS,
            Operation.READ_USER_BY_ID,
            Operation.READ_USER_BY_NAME,
            Operation.READ_PROFILE,
            Operation.EXIST_USER
    )),
    USER(List.of(
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
