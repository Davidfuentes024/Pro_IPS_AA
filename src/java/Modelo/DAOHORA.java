package Modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author caki-
 */
public class DAOHORA extends Conexion {

    public List<Hora> listarHoras(Date date, Consultorio consultorio, Sede sede, Usuario Doctor) throws Exception {
        List<Hora> horasOcupadas;
        Hora tim;
        ResultSet rs = null;
        if (sede.getCodigo() == 0 || consultorio.getCodigo() == 0) {
            return new ArrayList<>();
        }
        if (Doctor == null) {
            return new ArrayList<>();
        }
        String sql = "SELECT C.HORA FROM citas C "
                + "WHERE C.FECHA = '" + date + "' "
                + "AND C.IDSEDE = '" + sede.getCodigo() + "' "
                + "AND C.IDCONSULTORIO = '" + consultorio.getCodigo() + "' "
                + "AND C.DOCTOR_ID = '" + Doctor.getId_usuario() + "' "
                + "AND C.ESTADO";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            horasOcupadas = new ArrayList<>();
            int count = 0;
            while (rs.next() == true) {
                tim = new Hora();
                tim.setHora(rs.getTime("HORA"));
                tim.setCodigo(count);
                tim.setEstado(true);
                tim.setNombre(tim.getHora().toString());
                horasOcupadas.add(tim);
            }
            this.cerrar(true);
        } catch (Exception e) {
            throw e;
        } finally {
        }

        // Crear una lista para almacenar los tiempos
        List<Hora> horasDisponibles = new ArrayList<>();

        // Crear un objeto Calendar para manejar las horas
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8); // Establecer la hora inicial
        cal.set(Calendar.MINUTE, 0); // Establecer los minutos iniciales
        cal.set(Calendar.SECOND, 0); // Establecer los segundos iniciales
        LocalDate localDate = date.toLocalDate();
        DayOfWeek diaSemana = localDate.getDayOfWeek();
        if (diaSemana == DayOfWeek.SUNDAY) {

        } else if (diaSemana == DayOfWeek.SATURDAY) {
            // Crear un bucle para agregar horas a la lista
            int count = 0;
            while (cal.get(Calendar.HOUR_OF_DAY) <= 11 && cal.get(Calendar.MINUTE) <= 30) {
                // Obtener la hora actual del Calendar y agregarla a la lista
                Hora temp = new Hora();
                temp.setHora(new Time(cal.getTimeInMillis()));
                temp.setCodigo(count);
                temp.setEstado(true);
                temp.setNombre(temp.getHora().toString());
                horasDisponibles.add(temp);
                count++;

                // Agregar 30 minutos al tiempo actual del Calendar
                cal.add(Calendar.MINUTE, 30);
            }
        } else {
            // Crear un bucle para agregar horas a la lista
            int count = 0;
            while (cal.get(Calendar.HOUR_OF_DAY) <= 16 && cal.get(Calendar.MINUTE) <= 30) {
                // Obtener la hora actual del Calendar y agregarla a la lista
                Hora temp = new Hora();
                temp.setHora(new Time(cal.getTimeInMillis()));
                temp.setCodigo(count);
                temp.setEstado(true);
                temp.setNombre(temp.getHora().toString());
                horasDisponibles.add(temp);
                count++;

                // Agregar 15 minutos al tiempo actual del Calendar
                cal.add(Calendar.MINUTE, 30);
            }
        }
        for (int i = 0; i < horasDisponibles.size(); i++) {
            for (int j = 0; j < horasOcupadas.size(); j++) {
                if (horasDisponibles.get(i).getHora().toString().equals(horasOcupadas.get(j).getHora().toString())) {
                    horasDisponibles.get(i).setEstado(false);
                }
            }
        }

        return horasDisponibles;

    }
}
