package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient in the patient list identified by their Identity Number.\n"
            + "Parameters: i/NRIC (must be 9 characters)\n"
            + "Example: " + COMMAND_WORD + PREFIX_IDENTITY_NUMBER + "S1234567A";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final IdentityNumber identityNumber;
    private final Index targetIndex;

    public DeleteCommand(IdentityNumber identityNumber) {
        this.identityNumber = identityNumber;
        this.targetIndex = null;
    }

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.identityNumber = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (identityNumber == null) {
            return deleteByIndex(model);
        } else {
            return deleteByIdentityNumber(model);
        }
    }

    private CommandResult deleteByIdentityNumber(Model model) throws CommandException{
        List<Person> lastShownList = model.getPersonList();
        Person personToDelete = null;

        // Find the person by identity number
        for (Person person : lastShownList) {
            if (person.getIdentityNumber().equals(identityNumber)) {
                personToDelete = person;
                break;
            }
        }

        // If person was not found, throw an exception
        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private CommandResult deleteByIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return identityNumber.equals(otherDeleteCommand.identityNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("identityNumber", identityNumber)
                .toString();
    }
}
