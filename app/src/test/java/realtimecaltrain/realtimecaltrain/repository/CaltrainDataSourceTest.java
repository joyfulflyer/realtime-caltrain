package realtimecaltrain.realtimecaltrain.repository;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CaltrainDataSourceTest {

    @Rule
    public TestRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private Observer<String> mockObserver;



    @Test
    public void test() throws Exception {
        mockObserver = mock(Observer.class);


        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String argument = invocation.getArgument(0);
                assertNotNull(argument);
                System.out.println(argument);

                return null;
            }
        }).when(mockObserver).onChanged(anyString());



        File file = new File("/Users/bmckinley/repos/RealtimeCaltrain/app/src/main/res/raw/asset.html");

        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();

        while (br.ready()) {
            sb.append(br.readLine());
        }

        CaltrainDataSource caltrainDataSource = new CaltrainDataSource(null);

        caltrainDataSource.getData().observeForever(mockObserver);

        caltrainDataSource.handleHtmlString(sb.toString());


    }

}