package ar.edu.unlam.pb2;

import java.util.HashMap;
import java.util.Map;

public class Tienda {
	private String cuit;
	private String nombre;
	private Map<String, Vendible> productos;
	private Map<String, Integer> stockProductos;
	private Map<String, Cliente> clientes;
	private Map<String, Vendedor> vendedores;
	private Map<String, Venta> ventas;
	private Map<String, Servicio> servicios;

	public Tienda(String cuit, String nombre) {
		this.cuit = cuit;
		this.nombre = nombre;
		this.productos = new HashMap<>();
		this.stockProductos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.vendedores = new HashMap<>();
		this.ventas = new HashMap<>();
		this.servicios = new HashMap<>();
	}

	public void agregarProducto(Producto producto) {
		productos.put(producto.getCodigo(), producto);
	}

	public void agregarProducto(Producto producto, Integer cantidad) {
		productos.put(producto.getCodigo(), producto);
		stockProductos.put(producto.getCodigo(), cantidad);
	}

	public void agregarServicio(Servicio servicio) {
		servicios.put(servicio.getCodigo(), servicio);
	}

	public void agregarCliente(Cliente cliente) {
		clientes.put(cliente.getCuit(), cliente);
	}

	public void agregarVendedor(Vendedor vendedor) {
		vendedores.put(vendedor.getDni(), vendedor);
	}

	public void agregarVenta(Venta venta) {
		ventas.put(venta.getCodigo(), venta);
	}

	public void agregarServicioAVenta(String codigoVenta, Vendible vendible) {
		if (ventas.containsKey(codigoVenta)) {
			Venta venta = ventas.get(codigoVenta);
			if (vendibleDisponible(vendible)) {
				venta.agregarVendible(vendible);
			}
		}
	}

	public void agregarProductoAVenta(String codigoVenta, Vendible vendible, Integer cantidad)
			throws StockInsuficienteException {
		if (ventas.containsKey(codigoVenta)) {
			Venta venta = ventas.get(codigoVenta);
			if (vendibleDisponible(vendible)) {
				venta.agregarVendible(vendible);
				if (vendible instanceof Producto) {
					Producto producto = (Producto) vendible;
					reducirStockProducto(producto, cantidad);
				}
			}
		}
	}

	public Vendible getVendible(String codigo) throws VendibleInexistenteException {
		if (!productos.containsKey(codigo) && !servicios.containsKey(codigo)) {
			throw new VendibleInexistenteException("El producto o servicio con el código " + codigo + " no existe");
		}
		if (productos.containsKey(codigo)) {
			return productos.get(codigo);
		}
		return servicios.get(codigo);
	}

	public Integer getStock(Producto producto) {
		String codigo = producto.getCodigo();
		if (stockProductos.containsKey(codigo)) {
			return stockProductos.get(codigo);
		}
		return null;
	}

	public Cliente getCliente(String cuit) throws ClienteInexistenteException {
		if (!clientes.containsKey(cuit)) {
			throw new ClienteInexistenteException("El cliente con CUIT " + cuit + " no existe");
		}
		return clientes.get(cuit);
	}

	public Vendedor getVendedor(String dni) throws VendedorInexistenteException {
		if (!vendedores.containsKey(dni)) {
			throw new VendedorInexistenteException("El vendedor con DNI " + dni + " no existe");
		}
		return vendedores.get(dni);
	}

	private boolean vendibleDisponible(Vendible vendible) {
		return productos.containsKey(vendible.getCodigo()) || servicios.containsKey(vendible.getCodigo());
	}

	private void reducirStockProducto(Producto producto, Integer cantidad) throws StockInsuficienteException {
		String codigoProducto = producto.getCodigo();
		if (stockProductos.containsKey(codigoProducto)) {
			Integer stockActual = stockProductos.get(codigoProducto);
			if (stockActual >= cantidad) {
				stockProductos.put(codigoProducto, stockActual - cantidad);
			} else {
				throw new StockInsuficienteException("Stock insuficiente para la venta");
			}
		}
	}
}