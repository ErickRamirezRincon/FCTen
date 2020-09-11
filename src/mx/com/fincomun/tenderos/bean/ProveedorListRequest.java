package mx.com.fincomun.tenderos.bean;

import mx.com.fincomun.tenderos.util.Util;

public class ProveedorListRequest {
	
	private String idUsuario;

	public long getIdUsuario() {
		return Util.convertStringToLong(idUsuario);
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "ProveedorListRequest [idUsuario=" + idUsuario + "]";
	}
}