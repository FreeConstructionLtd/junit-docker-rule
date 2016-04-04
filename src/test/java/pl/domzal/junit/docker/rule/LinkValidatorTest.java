package pl.domzal.junit.docker.rule;

import org.junit.Test;

public class LinkValidatorTest {

    @Test(expected = InvalidParameter.class)
    public void failNull() {
        LinkNameValidator.validatedContainerLink(null);
    }

    @Test(expected = InvalidParameter.class)
    public void failEmpty() {
        LinkNameValidator.validatedContainerLink("");
    }

    @Test(expected = InvalidParameter.class)
    public void failInvalidCharsInName() {
        LinkNameValidator.validatedContainerLink("host%");
    }

    @Test
    public void okValidName() {
        LinkNameValidator.validatedContainerLink("abcDEF_-");
    }

    @Test
    public void okValidNameAndAlias() {
        LinkNameValidator.validatedContainerLink("db:some_container");
    }

    @Test(expected = InvalidParameter.class)
    public void failTooManyColons() {
        LinkNameValidator.validatedContainerLink("db:some_container:other_container");
    }

}
