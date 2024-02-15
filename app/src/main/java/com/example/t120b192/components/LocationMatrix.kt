package com.example.t120b192.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.example.t120b192.entities.Measurement
import java.lang.Math.abs

class LocationMatrix(context: Context?, attrs: AttributeSet?) : View(context, attrs){
    private var cellSize: Float = 0f
    private val cellPaint: Paint = Paint()
    private val linePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val colorGreen = Color.GREEN
    private val colorRed = Color.RED
    private val colorWhite = Color.WHITE
    private val colorYellow = Color.YELLOW
    private val colorBlack = Color.BLACK
    private var highlightX: Int? = null
    private var highlightY: Int? = null
    private var minX = 0
    private var minY = 0

    fun setHighlightCoordinates(x: Int, y: Int) {
        highlightX = x
        highlightY = y
        invalidate() // Request a redraw
    }

    private var isLoading = true

    fun setLoading(loading: Boolean) {
        isLoading = loading
        invalidate() // Request a redraw of the view after changing the loading state
    }


    private lateinit var gridData: Triple<Int, Int, Array<IntArray>>

    fun initializeWithMeasurements(measurements: List<Measurement>) {
        var measurements = measurements
        calculateGridInfo(measurements)
        invalidate() // Request a redraw of the view after initializing the measurements
    }
    init {
        //cell color
        cellPaint.color = colorWhite
        //line color
        linePaint.color = colorBlack
        linePaint.strokeWidth= 2F
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun calculateGridInfo(measurements: List<Measurement>) {
        // Find the minimum and maximum x values
        minX = measurements.minByOrNull { it.x }?.x ?: 0
        val maxX = measurements.maxByOrNull { it.x }?.x ?: 0

        // Find the minimum and maximum y values
        minY = measurements.minByOrNull { it.y }?.y ?: 0
        val maxY = measurements.maxByOrNull { it.y }?.y ?: 0

        // Calculate the number of rows and columns based on the ranges
        val numColumns = maxX + kotlin.math.abs(minX) + 1
        val numRows = maxY + kotlin.math.abs(minY) + 1

        //val numRows = maxX + kotlin.math.abs(minX) + 1
        //val numColumns = maxY + kotlin.math.abs(minY) + 1
        cellSize = width.toFloat() / numColumns
        textPaint.textSize = cellSize / 2f
        // Initialize a 2D grid with all zeros
        val grid = Array(numRows) { IntArray(numColumns) { 0 } }

        // Update the grid based on measurements
        for (measurement in measurements) {
            val colIndex = measurement.x - minX
            val  rowIndex= measurement.y - minY

            // Check if x and y are within the expected range
            if (rowIndex in 0 until numRows && colIndex in 0 until numColumns) {
                // If yes, mark the position as 1
                grid[rowIndex][colIndex] = 1
            }
        }

        gridData = Triple(numRows, numColumns, grid)
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.contentDescription = "Location Matrix View"
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isLoading) {
            // Draw loading text
            val loadingText = "Loading..."
            val x = width / 2f
            val y = height / 2f
            canvas.drawText(loadingText, x, y, textPaint)
            return
        }

        val numRows = gridData.first
        val numColumns = gridData.second

        //Log.d("post", "rows: $numRows")

        //Log.d("post", "columns: $numColumns")
        //grid cells
        for (row in 0 until numRows) {
            for (col in 0 until numColumns) {
                val left = col * cellSize + cellSize
                val top = row * cellSize + cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                val text = gridData.third[row][col].toString() // Access the 2D array

                textPaint.color = colorWhite
                // Set text background color based on the value
                if (text == "1") {
                    cellPaint.color = colorGreen
                }
                else {
                    cellPaint.color = colorRed
                }
                if (highlightX != null && highlightY != null){
                    val Yindex = (highlightY ?: 0) + abs((minY ?: 0))
                    val Xindex = (highlightX ?: 0) + abs((minX ?: 0))
                    if (row == Yindex && col == Xindex)
                    {
                        cellPaint.color = colorYellow
                    }
                }

                canvas.drawRect(left, top, right, bottom, cellPaint);

                //horizontal lines
                if (row < numRows - 1) {
                    val y = ((row + 1) * cellSize).toFloat()
                    canvas.drawLine(left.toFloat(), y, right.toFloat(), y, linePaint)
                }

                //vertical lines
                if (col < numColumns - 1) {
                    val x = ((col + 1) * cellSize).toFloat()
                    canvas.drawLine(x, top.toFloat(), x, bottom.toFloat(), linePaint)
                }

                //putting the text to the middle of the cell
                val x = (left + right) / 2f
                val y = (top + bottom) / 2f + textPaint.textSize / 2f

                canvas.drawText(text, x, y, textPaint)

                // Draw row and column numbers
                if (col == 0) {
                    textPaint.color = colorBlack
                    val rowNumber = row + minY
                    val xRow = left - textPaint.textSize / 2f
                    val yRow = (top + bottom) / 2f + textPaint.textSize / 2f
                    canvas.drawText(rowNumber.toString(), xRow, yRow, textPaint)
                    //Log.d("LocationMatrix", "rowNumber: $rowNumber")
                }

                if (row == 0) {
                    textPaint.color = colorBlack
                    val colNumber = col + minX
                    val xCol = (left + right) / 2f
                    val yCol = top - textPaint.textSize / 2f
                    canvas.drawText(colNumber.toString(), xCol, yCol, textPaint)
                }

            }
        }
    }
}