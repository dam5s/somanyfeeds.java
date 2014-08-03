package somanyfeeds.sources;

import com.somanyfeeds.sources.*;
import org.junit.Test;

import static com.somanyfeeds.sources.SourceEntity.sourceEntityBuilder;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SourcePresenterTest {

    @Test
    public void isSelected() {
        SourceEntity source = sourceEntityBuilder().slug("my-blog").build();
        SourcePresenter presenter;

        presenter = new SourcePresenter(source, asList("my-blog", "my-photos", "my-code"));
        assertTrue(presenter.isSelected());

        presenter = new SourcePresenter(source, asList("my-photos", "my-code"));
        assertFalse(presenter.isSelected());
    }

    @Test
    public void testGetLink_WhenSelected() {
        SourceEntity source = sourceEntityBuilder().slug("my-blog").build();
        SourcePresenter presenter = new SourcePresenter(source, asList("my-blog", "my-photos", "my-code"));

        assertThat(presenter.getLink(), equalTo("/my-code,my-photos"));
    }

    @Test
    public void testGetLink_WhenNotSelected() {
        SourceEntity source = sourceEntityBuilder().slug("my-blog").build();
        SourcePresenter presenter = new SourcePresenter(source, asList("my-photos", "my-code"));

        assertThat(presenter.getLink(), equalTo("/my-blog,my-code,my-photos"));
    }
}
