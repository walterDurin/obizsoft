package test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.lanit.dibr.utils.core.*;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * User: Vova
 * Date: 09.12.12
 * Time: 18:44
 */

public class TestRepeatedSource {

    @Mock
    Source source;

    private boolean stop = false;

    @Before
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(source.readLine())
                .thenReturn("[block]")
                .thenReturn("aa 111")
                .thenReturn("[block]")
                .thenReturn("aa 222")
                .thenReturn("[block]")
                .thenReturn("aa 333")
                .thenReturn("[block]")
                .thenReturn("bb 111")
                .thenReturn("[block]")
                .thenReturn("bb 222")
                .thenReturn("[block]")
                .thenReturn("bb 333")
                .thenReturn("[block]")
                .thenReturn("cc 111")
                .thenReturn("[block]")
                .thenReturn("cc 222")
                .thenReturn("[block]")
//                .thenReturn("cc 333")
                .then(new Answer<Object>() {
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        stop = true;
                        return "cc 333";
                    }
                });
        ;
    }

    @Test
    public void testFilterScreen_Grep() throws IOException {
        FilterScreen filterScreen = new FilterScreen();
        filterScreen.add(new GrepFilter("111", false));
        filterScreen.add(new GrepFilter("222", false));
        Source filteredSource = filterScreen.apply(source);

        String result = "";
        for (stop = false; !stop;) {
            result+=filteredSource.readLine()+"\n";
        }
        Assert.assertEquals(
                "aa 111\n" +
                "aa 222\n" +
                "SKIP_LINE\n" +
                "bb 111\n" +
                "bb 222\n" +
                "SKIP_LINE\n" +
                "cc 111\n" +
                "cc 222\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n",
                result);
    }

    @Test
    public void testFilterScreen_Block() throws IOException {
        FilterScreen filterScreen = new FilterScreen();
        filterScreen.add(new BlockFilter("\\[block\\]", "111", false));
        filterScreen.add(new BlockFilter("\\[block\\]", "222", false));
        Source filteredSource = filterScreen.apply(source);

        String result = "";
        for (stop = false; !stop;) {
            result+=filteredSource.readLine()+"\n";
        }
        Assert.assertEquals(
                "aa 111\n" +
                "aa 222\n" +
                "SKIP_LINE\n" +
                "bb 111\n" +
                "bb 222\n" +
                "SKIP_LINE\n" +
                "cc 111\n" +
                "cc 222\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n",
                result);
    }

    @Test
    public void testFilteredSource_Block() throws IOException {

        FilteredSource filteredSource = new FilteredSource(source);
//        filteredSource.addFilterToScreen(new BlockFilter("\\[block\\]", "111", false));
//        filteredSource.addFilterToScreen(new BlockFilter("\\[block\\]", "333", false));
        filteredSource.addFilterToQueue(new BlockFilter("\\[block\\]", "aa", true));

        String result = "";
        for (stop = false; !stop;) {
            result+=filteredSource.readLine()+"\n";
        }
        Assert.assertEquals(
                "aa 111\n" +
                "SKIP_LINE\n" +
                "aa 333\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n",
                result);
    }

    @Test
    public void testFilteredSource_Grep() throws IOException {

        FilteredSource filteredSource = new FilteredSource(source);
        filteredSource.addFilterToScreen(new GrepFilter("111", false));
        filteredSource.addFilterToScreen(new GrepFilter("333", false));
        filteredSource.addFilterToQueue(new GrepFilter("aa", false));

        String result = "";
        for (stop = false; !stop;) {
            result+=filteredSource.readLine()+"\n";
        }
        Assert.assertEquals(
                "aa 111\n" +
                "SKIP_LINE\n" +
                "aa 333\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n" +
                "SKIP_LINE\n",
                result);
    }

}
