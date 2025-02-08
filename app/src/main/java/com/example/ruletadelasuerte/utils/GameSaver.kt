package com.example.ruletadelasuerte.utils
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameSaver(context: Context) {
    private val dbHelper = BBDDHanddler(context)

    /**
     * Insertar una partida en la base de datos.
     */
    fun insertGame(player1: String, winnings1: Int, player2: String, winnings2: Int, player3: String, winnings3: Int): Long {
        val db = dbHelper.writableDatabase

        // Determinar el ganador
        val winner = when {
            winnings1 > winnings2 && winnings1 > winnings3 -> player1
            winnings2 > winnings1 && winnings2 > winnings3 -> player2
            winnings3 > winnings1 && winnings3 > winnings2 -> player3
            else -> "Empate"
        }

        // Obtener la fecha y hora actual en formato "dd/MM/yyyy HH:mm:ss"
        val dateTime = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())

        val values = ContentValues().apply {
            put(BBDDHanddler.COLUMN_PLAYER1, player1)
            put(BBDDHanddler.COLUMN_WINNINGS1, winnings1)
            put(BBDDHanddler.COLUMN_PLAYER2, player2)
            put(BBDDHanddler.COLUMN_WINNINGS2, winnings2)
            put(BBDDHanddler.COLUMN_PLAYER3, player3)
            put(BBDDHanddler.COLUMN_WINNINGS3, winnings3)
            put(BBDDHanddler.COLUMN_WINNER, winner)
            put(BBDDHanddler.COLUMN_DATE_TIME, dateTime)
        }

        return db.insert(BBDDHanddler.TABLE_GAMES, null, values).also {
            db.close()
        }
    }

    /**
     * Obtener todas las partidas jugadas.
     */
    fun getAllGames(): List<Map<String, String>> {
        val db = dbHelper.readableDatabase
        val gameList = mutableListOf<Map<String, String>>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${BBDDHanddler.TABLE_GAMES}", null)

        if (cursor.moveToFirst()) {
            do {
                val gameData = mapOf(
                    "id" to cursor.getInt(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_ID)).toString(),
                    "player1" to cursor.getString(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_PLAYER1)),
                    "winnings1" to cursor.getInt(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_WINNINGS1)).toString(),
                    "player2" to cursor.getString(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_PLAYER2)),
                    "winnings2" to cursor.getInt(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_WINNINGS2)).toString(),
                    "player3" to cursor.getString(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_PLAYER3)),
                    "winnings3" to cursor.getInt(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_WINNINGS3)).toString(),
                    "winner" to cursor.getString(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_WINNER)),
                    "date_time" to cursor.getString(cursor.getColumnIndexOrThrow(BBDDHanddler.COLUMN_DATE_TIME))
                )
                gameList.add(gameData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return gameList
    }
}