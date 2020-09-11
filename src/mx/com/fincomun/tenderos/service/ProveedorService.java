package mx.com.fincomun.tenderos.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.fincomun.tenderos.bean.Empresa;
import mx.com.fincomun.tenderos.bean.Proveedor;
import mx.com.fincomun.tenderos.bean.ProveedorRequest;
import mx.com.fincomun.tenderos.bean.ProveedoresResponse;
import mx.com.fincomun.tenderos.dao.ProveedorDao;

@Service
public class ProveedorService {
	
	@Autowired
	private ProveedorDao proveedorDao;
	
	private static Logger log = Logger.getLogger(ProveedorService.class);
	
	public int altaProveedor(ProveedorRequest request) throws Exception {
		int error = 0;
		if(!proveedorExiste(request)){
			int idEmpresa = insertEmpresa(request.getNombreEmpresa());
			request.setIdEmpresa(idEmpresa);
			log.info("Proveedor: ["+request.toString()+"]");
			if(!proveedorDao.insertProveedor(request)){
				error = -2; 
			} 
		} else {
			error = -1;
		}
		log.info("Alta Proveedor: ["+error+"]");
		return error;
	}
	
	public boolean modificaProveedor(ProveedorRequest request) throws Exception {
		boolean isModifica = false;
		log.info("Proveedor: ["+request.toString()+"]");
		isModifica = proveedorDao.updateProveedor(request);
		log.info("Modifica Proveedor: ["+isModifica+"]");
		return isModifica;
	}
	
	public boolean eliminaProveedor(ProveedorRequest request) throws Exception {
		boolean isElimina = false;
		log.info("Proveedor: ["+request.toString()+"]");
		isElimina = proveedorDao.deleteProveedor(request);
		log.info("Elimina Proveedor: ["+isElimina+"]");
		return isElimina;
	}
	
	public boolean proveedorExiste(ProveedorRequest request) throws Exception {
		boolean isExiste = false;
		log.info("Buscando Proveedor: ["+request.toString()+"]");
		List<Proveedor> proveedores = proveedorDao.getListProveedores(request.getIdUsuario());
		if(proveedores != null && proveedores.size() > 0){
			for(Proveedor bean : proveedores){
				if(bean.getNombreProveedor().toUpperCase().equals(request.getNombreProveedor().toUpperCase())){
					isExiste = true;
					break;
				} else if(bean.getCorreo().toUpperCase().equals(request.getCorreo().toUpperCase())){
					isExiste = true;
					break;
				}
			}
		}
		log.info("Proveedor Existe: ["+isExiste+"]");
		return isExiste;
	}
	
	public ProveedoresResponse getProveedores(long idUsuario) throws Exception {
		ProveedoresResponse response = null;
		
		List<Proveedor> proveedores = proveedorDao.getListProveedoresEmpresa(idUsuario);
		if(proveedores != null && proveedores.size() > 0){
			response = new ProveedoresResponse();
			response.setProveedores(proveedores);
			log.info("Se encontraron ["+proveedores.size()+"] proveedores.");
		}
		
		return response;
	}
	
	private int insertEmpresa(String empresa){
		int idEmpresa = 0;
		List<Empresa> empresas = proveedorDao.getEmpresas();
		if(empresas != null && empresas.size() > 0){
			log.info("Buscando empresa en lista ["+empresas.size()+"]");
			for(Empresa bean : empresas){
				if(bean.getNombre().toUpperCase().equals(empresa.toUpperCase())){
					idEmpresa = bean.getId();
					break;
				} 
			}
			
			if(idEmpresa == 0){
				log.info("Agregar nueva empresa: ["+empresa+"]");
				idEmpresa = proveedorDao.insertEmpresa(empresa);
			}
			
		} else {
			log.info("Agregar nueva empresa: ["+empresa+"]");
			idEmpresa = proveedorDao.insertEmpresa(empresa);
		}
		log.info("IdEmpresa: ["+idEmpresa+"]");
		return idEmpresa;
	}
	

}
