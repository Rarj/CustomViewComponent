package rio.arj.customviewcomponents

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import rio.arj.customviewcomponents.databinding.CustomToolbarSearchBinding

interface ButtonCancelClickedListener {
  fun onClicked()
}

class CustomToolbarSearch(context: Context, attrs: AttributeSet) :
  ConstraintLayout(context, attrs) {

  companion object {
    private const val FLAG_INPUT_TYPE_TEXT = 1
    private const val FLAG_INPUT_TYPE_NUMBER = 2
  }

  private var listener: ButtonCancelClickedListener? = null

  var binding: CustomToolbarSearchBinding = DataBindingUtil.inflate(
    LayoutInflater.from(context),
    R.layout.custom_toolbar_search,
    this,
    true
  )


  init {
    val typedArray = context.theme.obtainStyledAttributes(
      attrs,
      R.styleable.CustomToolbarSearch,
      0, 0
    )

    val inputType = typedArray.getInt(R.styleable.CustomToolbarSearch_inputType, -1)

    setAttribute(inputType)
  }

  private fun setAttribute(inputType: Int) {
    if (inputType == FLAG_INPUT_TYPE_TEXT) {
      binding.textQuery.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
    } else if (inputType == FLAG_INPUT_TYPE_NUMBER) {
      binding.textQuery.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
    }

    binding.buttonCancel.setOnClickListener {
      listener?.onClicked()
    }
  }

  fun getQuery(): String {
    return binding.textQuery.text.toString()
  }

  fun setQuery(query: String) {
    binding.textQuery.setText(query)
  }

  fun setButtonCancelListener(listenerButton: ButtonCancelClickedListener) {
    this.listener = listenerButton
  }

  object CustomBinding {
    @InverseBindingAdapter(attribute = "textQuery")
    @JvmStatic
    fun getTextQuery(view: CustomToolbarSearch): String {
      return view.getQuery()
    }

    @BindingAdapter("app:textQuery")
    @JvmStatic
    fun setTextQuery(view: CustomToolbarSearch, value: String?) {
      if (value != null) {
        if (view.getQuery() != value) view.setQuery(value)
      }
    }

    @BindingAdapter("app:textQueryAttrChanged")
    @JvmStatic
    fun textQueryListener(view: CustomToolbarSearch, listener: InverseBindingListener) {
      view.binding.textQuery.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
          listener.onChange()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
      })
    }
  }
}