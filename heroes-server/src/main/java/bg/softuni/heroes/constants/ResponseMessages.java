package bg.softuni.heroes.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ResponseMessages {

    public static final String INVALID_SLOT = "Invalid Slot!";
    public static final String ITEM_EXISTS = "Item with name - %s already exist!";
    public static final String ITEM_NAME_NOT_EXISTS = "Item with provided name does not exist!";
    public static final String ITEM_ID_NOT_EXISTS = "Item with given id does not exist!";

    public static final String SUCCESSFUL_ITEM_CREATED = "Successfully created item";
    public static final String SUCCESSFUL_ITEM_EDITED = "Successfully updated item";
    public static final String SUCCESSFUL_ITEM_DELETED = "Successfully deleted item";


}
