package realtimecaltrain.realtimecaltrain.viewModels;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.concurrent.Executors;

import realtimecaltrain.realtimecaltrain.repository.CaltrainDataSource;
import retrofit2.http.HTTP;

public class MainViewModel extends ViewModel {


    private LiveData<Spanned> dataLiveData;

    private final CaltrainDataSource dataSource;

    public MainViewModel() {
        this(new CaltrainDataSource());
    }

    public MainViewModel(CaltrainDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public LiveData<Spanned> getData() {
        if (dataLiveData == null) {
            dataLiveData = Transformations.map(dataSource.getData(), new Function<String, Spanned>() {
                @Override
                public Spanned apply(String input) {
                    // Currently blank, but just in case.
                    Spanned spanned = Html.fromHtml(input);


                    return spanned;
                }
            });
        }
        return dataLiveData;
    }


    public void onTapped() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                dataSource.getNewData();
            }
        });
    }


}
