package org.tasksmicroservice.utils;

import org.modelmapper.spi.ErrorMessage;
import org.tasksmicroservice.dto.TaskRequestDto;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskInputValidation {
    private static Boolean isNull(String field) {
        return field == null || field.trim().isEmpty();
    }
    private static boolean dueDateIsValid(Date date) {
        if (date != null) {
            LocalDate dueDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return !LocalDate.now().isBefore(dueDate);
        } else {
            return false;
        }
    }

    public static List<ErrorMessage> validate(TaskRequestDto taskRequestDto) {
        var errors = new ArrayList<ErrorMessage>();

        if ( isNull(String.valueOf(taskRequestDto.getStatus())) ) {
            errors.add(new ErrorMessage("Status is required."));
        }

        if (isNull(taskRequestDto.getTitle())) {
            errors.add(new ErrorMessage("Title is required."));
        }

        if (dueDateIsValid(taskRequestDto.getDueDate())) {
            errors.add(new ErrorMessage("DueDate is not Valid."));
        }

        return errors;
    }
}
