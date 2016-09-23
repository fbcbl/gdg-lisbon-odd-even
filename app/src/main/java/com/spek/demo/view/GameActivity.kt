package com.spek.demo.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jakewharton.rxbinding.widget.RxCompoundButton
import com.jakewharton.rxbinding.widget.RxTextView
import com.spek.demo.R
import com.spek.demo.domain.game.OddEvenGame
import com.spek.demo.domain.game.Player
import com.spek.demo.presenter.GamePresenter
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GamePresenter.View {

    private lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        setupPresenter()

        setupViews()
    }

    override fun displayWinner(player: Player) {
        result.text = String.format(getString(R.string.game_winner), player.name)
    }

    override fun alertChoiceIsMissing() {
        AlertDialog.Builder(this)
                .setMessage(R.string.error_choice_is_missing)
                .setNeutralButton(R.string.neutral_got_it, { dialogInterface, i -> })
                .show()
    }

    private fun setupViews() {
        RxTextView.textChanges(oddPlayerPick).subscribe { text ->
            oddCheckbox.isEnabled = !text.isNullOrBlank()
        }

        RxTextView.textChanges(evenPlayerPick).subscribe { text ->
            evenCheckBox.isEnabled = !text.isNullOrBlank()
        }

        RxCompoundButton.checkedChanges(oddCheckbox).subscribe { isChecked ->
            if (isChecked) {
                try {
                    val number = oddPlayerPick.text.toString().toInt()
                    presenter.onOddPlayerChoiceConfirmation(number)
                } catch(exception: Exception) {
                    // empty
                }
            }
        }

        RxCompoundButton.checkedChanges(evenCheckBox).subscribe { isChecked ->
            if (isChecked) {
                try {
                    val number = evenPlayerPick.text.toString().toInt()
                    presenter.onEvenPlayerChoiceConfirmation(number)
                } catch(exception: Exception) {
                    // empty
                }
            }
        }

        getResult.setOnClickListener { presenter.onWinnerRequested() }

        reset.setOnClickListener {
            evenPlayerPick.requestFocus()
            presenter.reset()
        }
    }

    override fun setOddPlayerPickSectionEnabled(isEnabled: Boolean) {
        oddPlayerPick.isEnabled = isEnabled
        oddCheckbox.isEnabled = isEnabled
    }

    override fun setEvenPlayerPickSectionEnabled(isEnabled: Boolean) {
        evenPlayerPick.isEnabled = isEnabled
        evenCheckBox.isEnabled = isEnabled
    }

    override fun setGetResultsButtonVisibility(isVisible: Boolean) {
        if (isVisible) {
            getResult.animate().alpha(1f).withStartAction { getResult.visibility = View.VISIBLE }
        } else {
            getResult.animate().alpha(0f).withEndAction { getResult.visibility = View.INVISIBLE }
        }
    }

    override fun clearPicks() {
        oddPlayerPick.text = null
        oddCheckbox.isChecked = false

        evenPlayerPick.text = null
        evenCheckBox.isChecked = false
    }

    override fun clearWinner() {
        result.text = null
    }

    private fun setupPresenter() {
        presenter = GamePresenter(OddEvenGame(), this)
    }
}