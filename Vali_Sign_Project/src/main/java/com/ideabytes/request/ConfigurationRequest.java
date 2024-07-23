package com.ideabytes.request;


import jakarta.validation.constraints.NotBlank;

public class ConfigurationRequest {
	    @NotBlank
	    private String version;
        @NotBlank
	    private String deviceType;
        @NotBlank
        private String data;
        public String getVersion() {
        	return version;
           }
        public void setVersion(String version) {
          this.version = version;

	    }
       public String getDeviceType() {
    	  return deviceType;

	    }
        public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;

	    }
        public String getData() {
        return data;
        }

	    public void setData(String data) {

	        this.data = data;

	    }

	}


