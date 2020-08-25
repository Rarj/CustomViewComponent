package rio.arj.customviewcomponents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

  var query = MutableLiveData<String>()

}