package ar.edu.unlam.pb2;

public class Vendedor {
    private String dni;
    private String nombre;
    private Double comision;

    public Vendedor(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
        this.comision = 0.0;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

	public double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

}
