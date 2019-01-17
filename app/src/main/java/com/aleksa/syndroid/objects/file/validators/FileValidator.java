package com.aleksa.syndroid.objects.file.validators;

import com.aleksa.syndroid.library.validators.BaseValidator;
import com.aleksa.syndroid.library.validators.Rule;

import java.util.Arrays;

public class FileValidator extends BaseValidator
{
    {
        rules = Arrays.asList(
            new Rule("name", "show", "[a-z]+", true),
            new Rule("age", "show|list", "[0-9]+", false)
        );
    }
}
