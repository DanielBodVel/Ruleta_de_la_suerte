package com.example.ruletadelasuerte.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ruletadelasuerte.R

/**
 * Adaptador para mostrar las partidas en un RecyclerView.
 */
class GameAdapter(private val gameList: List<Map<String, String>>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fecha: TextView = view.findViewById(R.id.fecha)
        val nombreJugador1: TextView = view.findViewById(R.id.nombreJugador1)
        val nombreJugador2: TextView = view.findViewById(R.id.nombreJugador2)
        val nombreJugador3: TextView = view.findViewById(R.id.nombreJugador3)
        val gananciaJugador1: TextView = view.findViewById(R.id.gananciaJugador1)
        val gananciaJugador2: TextView = view.findViewById(R.id.gananciaJugador2)
        val gananciaJugador3: TextView = view.findViewById(R.id.gananciaJugador3)
        val txtWinner: TextView = view.findViewById(R.id.nombreJugadorganador)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partida, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.fecha.text = "Fecha: ${game["date_time"]}"
        holder.nombreJugador1.text = "${game["player1"]}"
        holder.nombreJugador2.text = "${game["player2"]}"
        holder.nombreJugador3.text = "${game["player3"]}"
        holder.gananciaJugador1.text = "${game["winnings1"]} €"
        holder.gananciaJugador2.text = "${game["winnings2"]} €"
        holder.gananciaJugador3.text = "${game["winnings3"]} €"
        holder.txtWinner.text = "${game["winner"]}"
    }

    override fun getItemCount(): Int = gameList.size
}
