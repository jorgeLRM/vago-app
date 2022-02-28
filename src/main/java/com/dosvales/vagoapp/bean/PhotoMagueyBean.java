package com.dosvales.vagoapp.bean;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.dosvales.vagoapp.util.GrowlMessenger.addMessage;
import static com.dosvales.vagoapp.util.GrowlMessenger.showMessage;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;
import com.dosvales.vagoapp.service.MagueyService;
import com.dosvales.vagoapp.service.PhotoMagueyService;
import com.dosvales.vagoapp.util.FacesUtil;
import com.dosvales.vagoapp.util.FilePath;

@Named
@ViewScoped
public class PhotoMagueyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PhotoMagueyService photoMagueyService;
	
	@Inject
	private MagueyService magueyService;
	
	private Maguey maguey;
	
	private PhotoMaguey photoMaguey;
	
	private List<PhotoMaguey> photos;
	
	public void load(String id) {
		if (id != null && id.length() > 0) {
			try {
				maguey = magueyService.findById(Long.valueOf(id));
				photos = photoMagueyService.findByMaguey(maguey);
			} catch (Exception ex) {}
		}
	}
	
	public void save(String name) throws Exception {
		PhotoMaguey newPhoto = new PhotoMaguey();
		newPhoto.setMaguey(maguey);
		newPhoto.setName(name);
		newPhoto = photoMagueyService.save(newPhoto);
		photos.add(newPhoto);
	}

	public void delete() {
		try {
			photoMagueyService.delete(photoMaguey);
			photos.remove(photoMaguey);
			FacesUtil.deleteFile(FilePath.MAGUEYES_PATH + File.separator + photoMaguey.getName());
			showMessage("Operación exitosa", "Foto eliminada exitosamente.", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		String name = UUID.randomUUID() + ".jpg";
		String newFile = FilePath.MAGUEYES_PATH + File.separator + name;
		try {
			FacesUtil.uploadFile(newFile, event.getFile().getInputStream());
			save(name);
		} catch (Exception ex) {
			addMessage("Error", "Ha ocurrido un error. Intente mas tarde", FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public String getPhotoPath(String name) {
		return FilePath.DOMAIN + File.separator + FilePath.MAGUEY_DIRECTORY + File.separator + name;
	}
	
	public Maguey getMaguey() {
		return maguey;
	}

	public void setMaguey(Maguey maguey) {
		this.maguey = maguey;
	}

	public List<PhotoMaguey> getPhotos() {
		return photos;
	}

	public PhotoMaguey getPhotoMaguey() {
		return photoMaguey;
	}

	public void setPhotoMaguey(PhotoMaguey photoMaguey) {
		this.photoMaguey = photoMaguey;
	}
}
