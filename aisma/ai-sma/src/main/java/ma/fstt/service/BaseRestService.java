package ma.fstt.service;

import java.util.List;

public interface BaseRestService<RES,ID> {
	
	public List<RES> getAll() ;
	
	public RES get( ID id) ;
	
	public RES create(RES resource);
	
	public RES update( ID id, RES resource);

	public void delete( ID id);

}
