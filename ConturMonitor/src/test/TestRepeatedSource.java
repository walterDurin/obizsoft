package test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.lanit.dibr.utils.core.FilterScreen;
import ru.lanit.dibr.utils.core.FilteredSource;
import ru.lanit.dibr.utils.core.GrepFilter;
import ru.lanit.dibr.utils.core.Source;

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
                .thenReturn("aa 111")
                .thenReturn("aa 222")
                .thenReturn("aa 333")
                .thenReturn("bb 111")
                .thenReturn("bb 222")
                .thenReturn("bb 333")
                .thenReturn("cc 111")
                .thenReturn("cc 222")
                .thenReturn("cc 333")
                .then(new Answer<Object>() {
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        stop = true;
                        return "stop here";
                    }
                });
        ;
    }

    @Test
    public void testFilterScreen() throws IOException {
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
    public void testFilteredSource() throws IOException {

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
