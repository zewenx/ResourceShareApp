package zewen.unimelb.VO;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class FetchVO extends RequestVO{
	private ResourceVO resourceTemplate;

	public ResourceVO getResource() {
		return resourceTemplate;
	}

	public void setResource(ResourceVO resource) {
		this.resourceTemplate = resource;
	}

}
