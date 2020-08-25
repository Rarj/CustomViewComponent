package rio.arj.customviewcomponents

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import rio.arj.customviewcomponents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  lateinit var binding: ActivityMainBinding
  lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    listener()
    observer()
  }

  private fun observer() {
    viewModel.query.observe(this, Observer {
      Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
    })
  }

  private fun listener() {
    binding.toolbarCustom.setButtonCancelListener(object : ButtonCancelClickedListener {
      override fun onClicked() {
        Toast.makeText(this@MainActivity, "Cancel Custom Toolbar", Toast.LENGTH_SHORT).show()
      }
    })
  }
}
