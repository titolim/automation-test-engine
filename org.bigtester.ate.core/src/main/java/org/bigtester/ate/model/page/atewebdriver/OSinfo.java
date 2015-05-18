/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.page.atewebdriver;

import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ostype defines ....
 * @author Jun Yang
 *
 */
    
public final class OSinfo {
	
	/** The os. */
	@Nullable
    private String osName;  
    
    /** The _instance. */
    private static OSinfo instance = new OSinfo();;  
    
    /** The platform. */
    private EPlatform platform ;
    
    /** The Constant OS64BIT. */
    final static private String OS64BIT = "64";
    
    /** The Constant OS32BIT. */
    final static private String OS32BIT = "32";
	
	/** The OSBIT. */
    @Nullable
	private String osBit; 
			
			
    
    /**
	 * @return the osBit
	 */
	public @Nullable String getOsBit() {
		this.osBit = System.getProperty("sun.arch.data.model");
		return osBit;
	}

	/**
	 * @param osBit the osBit to set
	 */
	public void setOsBit(String osBit) {
		this.osBit = osBit;
	}

	/**
	 * @return the osName
	 */
	public @Nullable String getOsName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * @param osName the osName to set
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * @return the platform
	 */
	public EPlatform getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(EPlatform platform) {
		this.platform = platform;
	}

	/**
     * Instantiates a new osinfo.
     */
    public OSinfo(){
        osName = System.getProperty("os.name").toLowerCase();
    	platform = EPlatform.Others;
    	osBit = getOsBit();
    }  
      
    /**
     * Checks if is linux.
     *
     * @return true, if is linux
     */
    public boolean isLinux(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS Linux name is not correct");
		} 
		else return os2.indexOf("linux")>=0;
    }  
      
    /**
     * Checks if is mac os.
     *
     * @return true, if is mac os
     */
    public boolean isMacOS(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS Mac name is not correct");
		} else return os2.indexOf("mac")>=0 && os2.indexOf("os")>0 && os2.indexOf('x')<0;
    }  
      
    /**
     * Checks if is mac osx.
     *
     * @return true, if is mac osx
     */
    public boolean isMacOSX(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS MacOSX name is not correct");
		} else return os2.indexOf("mac")>=0 && os2.indexOf("os")>0 && os2.indexOf('x')>0;
    }  
      
    /**
     * Checks if is windows.
     *
     * @return true, if is windows
     */
    public boolean isWindows(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS Windows name is not correct");
		} else return os2.indexOf("windows")>=0;
    }  
      
    /**
     * Checks if is os2.
     *
     * @return true, if is os2
     */
    public boolean isOS2(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS OS2 name is not correct");
		} else return os2.indexOf("os/2")>=0;
    }  
      
    /**
     * Checks if is solaris.
     *
     * @return true, if is solaris
     */
    public boolean isSolaris(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS Soliris name is not correct");
		} else return os2.indexOf("solaris")>=0;
    }  
      
    /**
     * Checks if is sun os.
     *
     * @return true, if is sun os
     */
    public boolean isSunOS(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS SunOS name is not correct");
		} else return os2.indexOf("sunos")>=0;
    }  
      
    /**
     * Checks if is MP eix.
     *
     * @return true, if is MP eix
     */
    public boolean isMPEiX(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS MPEiX name is not correct");
		} else return os2.indexOf("mpe/ix")>=0;
    }  
      
    /**
     * Checks if is hpux.
     *
     * @return true, if is hpux
     */
    public boolean isHPUX(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS HPUX name is not correct");
		} else return os2.indexOf("hp-ux")>=0;
    }  
      
    /**
     * Checks if is aix.
     *
     * @return true, if is aix
     */
    public boolean isAix(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS AIX name is not correct");
		} else return os2.indexOf("aix")>=0;
    }  
      
    /**
     * Checks if is o s390.
     *
     * @return true, if is o s390
     */
    public boolean isOS390(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS OS390 name is not correct");
		} else return os2.indexOf("os/390")>=0;
    }  
      
    /**
     * Checks if is free bsd.
     *
     * @return true, if is free bsd
     */
    public boolean isFreeBSD(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS FreeBSD name is not correct");
		} else return os2.indexOf("freebsd")>=0;
    }  
      
    /**
     * Checks if is irix.
     *
     * @return true, if is irix
     */
    public boolean isIrix(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS Irix name is not correct");			
		} else return os2.indexOf("irix")>=0;
    }  
      
    /**
     * Checks if is digital unix.
     *
     * @return true, if is digital unix
     */
    public boolean isDigitalUnix(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS DigittalUnix name is not correct");
		} else return os2.indexOf("digital")>=0 && os2.indexOf("unix")>0;
    }  
      
    /**
     * Checks if is netware.
     *
     * @return true, if is netware
     */
    public boolean isNetWare(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS NetWare name is not correct");
		} else return os2.indexOf("netware")>=0;
    }  
      
    /**
     * Checks if is OS f1.
     *
     * @return true, if is OS f1
     */
    public boolean isOSF1(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS OSF1 name is not correct");
		} else return os2.indexOf("osf1")>=0;
    }  
      
    /**
     * Checks if is open vms.
     *
     * @return true, if is open vms
     */
    public boolean isOpenVMS(){  
        String os2 = osName;
		if (os2 == null) {
			// TODO handle null value
			throw new IllegalStateException("OS OpenVMS name is not correct");
		} else return os2.indexOf("openvms")>=0;
    }  
      
    /**
     *  
     * Get the name of operating system .
     *
     * @return  the name of operating system
     */  
    public EPlatform getOSname(){  
    	if (isLinux()) { 
        	if(OS32BIT.equals(osBit)) {
                instance.platform = EPlatform.Linux_32;
             } else if (OS64BIT.equals(osBit)) {
             		instance.platform = EPlatform.Linux_64;
               } else throw new IllegalStateException("OS Bit is not correct"); 
    	}else if (isMacOSX()) {
            	if(OS32BIT.equals(osBit)) {
                   instance.platform = EPlatform.Mac_OS_X_32;
                }else if (OS64BIT.equals(osBit)) {
                		instance.platform = EPlatform.Mac_OS_X_64;
                }else throw new IllegalStateException("OS Bit is not correct");
    	}else if (isWindows()) {
               	if(OS32BIT.equals(osBit)) {
                    instance.platform = EPlatform.Windows_32;
               	} else if (OS64BIT.equals(osBit)) {
               		instance.platform = EPlatform.Windows_64;
                } else throw new IllegalStateException("OS Bit is not correct");	
    	}else if(isAix()){  
            instance.platform = EPlatform.AIX;  
        }else if (isDigitalUnix()) {  
            instance.platform = EPlatform.Digital_Unix;  
        }else if (isFreeBSD()) {  
            instance.platform = EPlatform.FreeBSD;  
        }else if (isHPUX()) {  
            instance.platform = EPlatform.HP_UX;  
        }else if (isIrix()) {  
            instance.platform = EPlatform.Irix;  
        }else if (isMacOS()) {  
            instance.platform = EPlatform.Mac_OS;  
        }else if (isMPEiX()) {  
            instance.platform = EPlatform.MPEiX;  
        }else if (isNetWare()) {  
            instance.platform = EPlatform.NetWare_411;  
        }else if (isOpenVMS()) {  
            instance.platform = EPlatform.OpenVMS;  
        }else if (isOS2()) {  
            instance.platform = EPlatform.OS2;  
        }else if (isOS390()) {  
            instance.platform = EPlatform.OS390;  
        }else if (isOSF1()) {  
            instance.platform = EPlatform.OSF1;  
        }else if (isSolaris()) {  
            instance.platform = EPlatform.Solaris;  
        }else if (isSunOS()) {  
            instance.platform = EPlatform.SunOS;  
        }else{  
            instance.platform = EPlatform.Others;  
        }  
         return instance.platform;  
        }

	/**
	 * @return the instance
	 */
	public static OSinfo getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(OSinfo instance) {
		OSinfo.instance = instance;
	}  
}  







