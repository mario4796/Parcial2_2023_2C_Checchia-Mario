package ar.edu.unlam.pb2;

import java.util.HashMap;
import java.util.Map;

public class Venta {
	private String codigo;
	private Cliente cliente;
	private Vendedor vendedor;
	private Map<String, Vendible> vendiblesVendidos;

	public Venta(String codigo, Cliente cliente, Vendedor vendedor) {
		this.codigo = codigo;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.vendiblesVendidos = new HashMap<>();
	}

	public String getCodigo() {
		return codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void agregarVendible(Vendible vendible) {
		vendiblesVendidos.put(vendible.getCodigo(), vendible);
	}

	public Double getTotal() {
		Double total = 0.0;
		for (Vendible vendible : vendiblesVendidos.values()) {
			total += vendible.getPrecio();
		}
		return total;
	}
}