package ar.edu.unlam.pb2;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestTienda {

	@Test
	public void queSePuedaAgregarProductos() throws VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d);
		tienda.agregarProducto(producto);
		Vendible productoActual = (Producto) tienda.getVendible(producto.getCodigo());
		assertEquals(producto, productoActual);
	}

	@Test
	public void queSePuedaAgregarStock() throws VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d);
		Integer cantidad = 10;
		tienda.agregarProducto(producto, cantidad);
		Integer stockActual = tienda.getStock((Producto) producto);
		assertEquals(cantidad, stockActual);

	}

	@Test
	public void queSePuedaAgregarUnCliente() throws ClienteInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitEjemplo = "30123456780";
		Cliente cliente = new Cliente(cuitEjemplo, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		Cliente clienteActual = tienda.getCliente(cuitEjemplo);
		assertEquals(cliente, clienteActual);

	}

	@Test
	public void queSePuedaAgregarUnVendedor() throws VendedorInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");
		tienda.agregarVendedor(vendedor);
		Vendedor vendedorActual = tienda.getVendedor(dniEjemplo);
		assertEquals(vendedor, vendedorActual);

	}

	@Test
	public void queSePuedaHacerUnaVentaDeUnProducto()
			throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");
		Vendible producto = new Producto("1", "Producto nuevo", 100d);
		Integer stockInicial = 10;
		tienda.agregarProducto((Producto) producto, stockInicial);
		Venta ticket = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(ticket);
		Integer cantidadVendida = 5;
		tienda.agregarProductoAVenta(ticket.getCodigo(), (Producto) producto, cantidadVendida);
		Integer stockEsperado = 5;
		Integer stockActual = tienda.getStock((Producto) producto);
		assertEquals(stockEsperado, stockActual);

	}

	@Test(expected = StockInsuficienteException.class)
	public void queNoSePuedaAgregarUnaVentaPorStockInsuficiente()
			throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");
		Producto producto = new Producto("1", "Producto nuevo", 100d);
		Integer stockInicial = 10;
		tienda.agregarProducto(producto, stockInicial);
		Venta ticket = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(ticket);
		Integer cantidadVendida = 15;
		tienda.agregarProductoAVenta(ticket.getCodigo(), producto, cantidadVendida);

	}

	@Test
	public void queSePuedaHacerUnaVentaDeUnServicio() throws VentaInexistenteException, VendibleInexistenteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");
		Servicio servicio = new Servicio("1", "Servicio Técnico", 100d, "2023-02-01", "2023-03-01");
		Vendible producto = new Producto("3", "Producto nuevo", 100d);
		Integer stockInicial = 10;
		tienda.agregarProducto((Producto) producto, stockInicial);

		tienda.agregarServicio((Servicio) servicio);
		Venta venta = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(venta);
		tienda.agregarServicioAVenta(venta.getCodigo(), servicio);
		Double totalEsperado = 100d;
		Double totalActual = venta.getTotal();
		assertEquals(totalEsperado, totalActual);

	}

	@Test
	public void queSePuedaHacerUnaVentaDeUnProductosYServicios()
			throws VentaInexistenteException, VendibleInexistenteException, StockInsuficienteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		String cuitCliente = "30123456780";
		Cliente cliente = new Cliente(cuitCliente, "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		String dniEjemplo = "12345678";
		Vendedor vendedor = new Vendedor(dniEjemplo, "Vendedor de ejemplo");

		Venta venta = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(venta);
		Vendible vendible;

		vendible = new Servicio("1", "Servicio Técnico", 100d, "2023-02-01", "2023-03-01");
		tienda.agregarServicio((Servicio) vendible);
		tienda.agregarServicioAVenta(venta.getCodigo(), (Servicio) vendible);

		vendible = new Producto("2", "Producto nuevo", 350d);
		Integer stockInicial = 10;
		tienda.agregarProducto((Producto) vendible, stockInicial);
		Integer cantidadVendida = 2;
		tienda.agregarProductoAVenta(venta.getCodigo(), (Producto) vendible, cantidadVendida);

		Double totalEsperado = 450d;
		Double totalActual = venta.getTotal();
		assertEquals(totalEsperado, totalActual);

	}

	/*
	 * @Test public void queSePuedaEstablecerElPorcentajeDeComisionDeUnVendedor() {
	 * Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo"); String
	 * dniEjemplo = "12345678"; Vendedor vendedor = new Vendedor(dniEjemplo,
	 * "Vendedor de ejemplo"); tienda.agregarVendedor(vendedor); double
	 * comisionEsperada = 10.0; vendedor.setComision(comisionEsperada); double
	 * comisionActual = vendedor.getComision(); assertEquals(comisionEsperada,
	 * comisionActual, 0.01); }
	 */
	@Test
	public void queSePuedaEstablecerElPorcentajeDeComisionDeUnVendedor() throws StockInsuficienteException {
		Tienda tienda = new Tienda("30123456780", "Tienda de ejemplo");
		Cliente cliente = new Cliente("30123456780", "Cliente de ejemplo");
		tienda.agregarCliente(cliente);
		Vendedor vendedor = new Vendedor("12345678", "Vendedor de ejemplo");
		tienda.agregarVendedor(vendedor);
		Venta venta = new Venta("C-0001", cliente, vendedor);
		tienda.agregarVenta(venta);
		Double porcentajeComision = 0.1;
		Producto producto = new Producto("1", "Producto de ejemplo", 300.0);
		tienda.agregarProducto(producto, 5);
		tienda.agregarProductoAVenta(venta.getCodigo(), producto, 2);
		vendedor.setComision(porcentajeComision);
		Double totalVenta = venta.getTotal();
		Double comisionEsperada = 30.0;
		Double comisionActual = totalVenta * porcentajeComision;
		assertEquals(comisionEsperada, comisionActual, 0.001);
	}

	@Test
	public void queSeCalculeElMontoTotalDeComisionesQueTieneUnVendedor() {

	}
}
