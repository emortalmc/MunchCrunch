package dev.emortal.munchcrunch.database

import net.minestom.server.MinecraftServer
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLStorage(credentials: Config) {
    private val logger = MinecraftServer.getExtensionManager().getExtension("MunchCrunch")?.logger

    private val host: String = credentials.host
    private val port: String = credentials.port
    private val username: String = credentials.username
    private val password: String = credentials.password
    private val database: String = credentials.database

    private var connection: Connection? = null


    fun insertIntoTable(table: String, columns: List<String>, vararg data: Values) {
        val statement = connection!!.createStatement()
        var values = ""
        var sqlColumns = "("
        for(i in 1..columns.size){
            if(i == columns.size){
                sqlColumns += columns[i-1]
                continue
            }
            sqlColumns += "${columns[i-1]},"
        }
        sqlColumns += ")"
        for(i in 1..data.size){
            values += "("
            for(y in 1..data[i-1].values.size){
                if(y == data[i-1].values.size){
                    values += if(data[i-1].values[y-1] is String){
                        "'${data[i-1].values[y-1]}'"
                    }else{
                        "${data[i-1].values[y-1]}"
                    }
                    continue
                }
                values += if(data[i-1].values[y-1] is String){
                    "'${data[i-1].values[y-1]}',"
                }else{
                    "${data[i-1].values[y-1]},"
                }
            }
            if(i == data.size){
                values += ")"
                continue
            }
            values += "),"
        }

        val sqlQuery = "INSERT INTO $table $sqlColumns VALUES $values"

        try{
            statement.executeUpdate(sqlQuery)
            logger!!.info("Successfully inserted $values into $table")
        }catch (ex: SQLException){
            ex.printStackTrace()
        }

    }

    /*fun get(table: String, column: String, where: String): Any {
        return
    }*/

    fun connect(){
        if(connection != null){
            if(connection!!.isClosed) {
                try{
                    connection = createConnection()
                    logger!!.info("Connected to ${database}!")
                }catch (ex: SQLException){
                    ex.printStackTrace()
                }
                return
            }
            logger!!.info("Is already connected to ${database}!")
        }else{
            try{
                connection = createConnection()
                logger!!.info("Connected to ${database}!")
            }catch (ex: SQLException){
                ex.printStackTrace()
            }
        }
    }

    private fun createConnection(): Connection {
        Class.forName("org.mariadb.jdbc.Driver")
        return DriverManager.getConnection(
            "jdbc:mariadb://${host}:${port}/${database}",
            username, password
        )
    }
    fun disconnect() {
        if (connection == null){
            logger!!.info("No connection to disconnect!")
            return
        }
        if(connection!!.isClosed) {
            logger!!.info("No connection to disconnect!")
            return
        }
        logger!!.info("Closed connection")
        connection!!.close()
    }
}