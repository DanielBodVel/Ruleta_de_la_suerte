package com.example.ruletadelasuerte.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Helper para gestionar la base de datos SQLite.
 */
class BBDDHanddler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ruleta.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos


        // Definición de la tabla de partidas jugadas
        const val TABLE_GAMES = "games"
        const val COLUMN_ID = "id"
        const val COLUMN_PLAYER1 = "player1"
        const val COLUMN_PLAYER2 = "player2"
        const val COLUMN_PLAYER3 = "player3"
        const val COLUMN_WINNINGS1 = "money1"
        const val COLUMN_WINNINGS2 = "money2"
        const val COLUMN_WINNINGS3 = "money3"
        const val COLUMN_WINNER = "winner"
        const val COLUMN_DATE_TIME = "date_time"

        // Query para crear la tabla de partidas jugadas
        private const val CREATE_TABLE_GAMES = """
            CREATE TABLE $TABLE_GAMES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PLAYER1 TEXT NOT NULL,
                $COLUMN_PLAYER2 TEXT NOT NULL,
                $COLUMN_PLAYER3 TEXT NOT NULL,
                $COLUMN_WINNINGS1 INTEGER NOT NULL,
                $COLUMN_WINNINGS2 INTEGER NOT NULL,
                $COLUMN_WINNINGS3 INTEGER NOT NULL,
                $COLUMN_WINNER TEXT NOT NULL,
                $COLUMN_DATE_TIME TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_GAMES) // Crea la tabla de partidas jugadas
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("ALTER TABLE $TABLE_GAMES ADD COLUMN new_column TEXT DEFAULT ''")
    }
}
