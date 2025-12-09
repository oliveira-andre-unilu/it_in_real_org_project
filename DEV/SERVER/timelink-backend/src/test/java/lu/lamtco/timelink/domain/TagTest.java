package lu.lamtco.timelink.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    void testTagEnumValues() {
        assertThat(Tag.values()).containsExactly(Tag.WORK, Tag.BREAK);
    }

    @Test
    void testValueOfTag() {
        assertThat(Tag.valueOf("WORK")).isEqualTo(Tag.WORK);
        assertThat(Tag.valueOf("BREAK")).isEqualTo(Tag.BREAK);
    }
}
