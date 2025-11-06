package com.example.feedmytummy.navFragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.feedmytummy.R
import java.io.File
import java.io.FileOutputStream


class GroceryFragment : Fragment() {
    private val groceryList = mutableListOf<String>()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_grocerie, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<android.widget.EditText>(R.id.editTextText7)
        val tvGroceryList = view.findViewById<android.widget.TextView>(R.id.tvGroceryList)
        val addButton = view.findViewById<android.widget.Button>(R.id.addButton)
        addButton.setOnClickListener {
            val item = editText.text.toString().trim()
            if (item.isNotEmpty()) {
                groceryList.add(item)
                editText.text.clear()
                tvGroceryList.text = groceryList.mapIndexed { i, s -> "${i + 1}. $s" }.joinToString("\n")
            }
        }
        val exportbutton = view.findViewById<android.widget.Button>(R.id.exportbutton)
        exportbutton.setOnClickListener {
            if (groceryList.isEmpty()) {
                Toast.makeText(requireContext(), "Grocery list is empty!", Toast.LENGTH_SHORT).show()
            } else {
                createPdf(groceryList)
            }
        }

        val backArrow = view.findViewById<android.widget.ImageView>(R.id.backArrow)
        backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AddFragment())
                .commit()
        }
        val profileIcon = view.findViewById<android.widget.ImageView>(R.id.profileIcon)
        profileIcon.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, UserprofileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
    // 📝 Generate PDF file
    private fun createPdf(groceryList: List<String>) {
        val pdfDocument = android.graphics.pdf.PdfDocument()
        val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 16f

        var yPosition = 50
        canvas.drawText("🛒 Grocery List", 50f, yPosition.toFloat(), paint)
        yPosition += 30

        groceryList.forEachIndexed { index, item ->
            canvas.drawText("${index + 1}. $item", 50f, yPosition.toFloat(), paint)
            yPosition += 25
        }

        pdfDocument.finishPage(page)

        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "GroceryList.pdf")

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(requireContext(), "PDF saved to Downloads: ${file.name}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error creating PDF: ${e.message}", Toast.LENGTH_LONG).show()
        }

        pdfDocument.close()
    }
}
