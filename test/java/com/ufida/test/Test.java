package com.ufida.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yonyou.encrypt.utils.AESUtils;


public class Test {
	
	public static void main(String args[]){
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String mkey = sd.format( new Date());
		try {
			String words = AESUtils.encrypt(String.format("%s@#%s", "yonyou", "1.0"), mkey, 16);
			System.out.println( words);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println(System.getProperty("file.encoding"));
        String content = "yonyou";
        String password = "BryanAndCelia";
        //加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password,16);
        System.out.println("加密后：" + parseByte2HexStr(encryptResult));
        //解密
        byte[] decryptResult = decrypt(encryptResult,password,16);
        System.out.println("解密后：" + new String(decryptResult));
        System.out.println("KeyGenerator 结果：");*/
    
	}
	
    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password  加密密码
     * @param keySize 密钥长度16,24,32
     * @return
     */
    public static byte[] encrypt(String content, String password, int keySize) {
            try {                              
        		SecretKeySpec key = new SecretKeySpec(ZeroPadding(password.getBytes(), keySize), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
                byte[] byteContent = content.getBytes();
                cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(byteContent);
                return result; // 加密
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
            } catch (InvalidKeyException e) {
                    e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
            } catch (BadPaddingException e) {
                    e.printStackTrace();
            }
            return null;
    }
    
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @param keySize 密钥长度16,24,32
     * @return
     */
    public static byte[] decrypt(byte[] content, String password, int keySize) {
            try { 
        		SecretKeySpec key = new SecretKeySpec(ZeroPadding(password.getBytes(), keySize), "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
                cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
                byte[] result = cipher.doFinal(content);
                return result; // 加密
            } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
            } catch (InvalidKeyException e) {
                    e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
            } catch (BadPaddingException e) {
                    e.printStackTrace();
            }
            return null;
    }
    
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
                String hex = Integer.toHexString(buf[i] & 0xFF);
                if (hex.length() == 1) {
                        hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
                return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    public static byte[] ZeroPadding(byte[] in,Integer blockSize){
    	Integer copyLen = in.length;
    	if (copyLen > blockSize) {
			copyLen = blockSize;
		}
    	byte[] out = new byte[blockSize];
    	System.arraycopy(in, 0, out, 0, copyLen);
    	return out;
    }
    

	/*private static Document buildXml(String rootName, Map<String, Object> param) {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("GBK");
		Element root = doc.addElement(rootName);
		if (param != null) {
			for (Entry<String, Object> entry : param.entrySet()) {
				Element ele = root.addElement(entry.getKey());
				ele.setText(entry.getValue().toString());
			}
		}
		return doc;
	}

	
	public static void main(String[] args){
		System.out.println( int.class.getSimpleName());
		Map<String,Object> condi = new HashMap<String, Object>();
		
		Document rootDoc = buildXml("root", condi);
		condi.clear();
		condi.put("user", "suer1");
		condi.put("pwd", "pwd1");
		Document ctl = buildXml("ctl", condi);
		condi.clear();
		condi.put("dddd", "tyfff");
		Document data = buildXml("data", condi);
		//System.out.println( ctl.asXML());
		//System.out.println( data.asXML());
		Element de = data.getRootElement();
		Element dc = ctl.getRootElement();
		Element ele1 = rootDoc.getRootElement().addElement("param").addAttribute("rname", "ctl").addAttribute("rmethod", "pCtlParam");
		ele1.add( dc);
		
		ele1 = rootDoc.getRootElement().addElement("param").addAttribute("rname", "data").addAttribute("rmethod", "pInParam");
		ele1.add(de);
		Element ele1 = rootDoc.getRootElement().addElement("param");
		ele1.addElement("name").addAttribute("type", "String");
		
		Element elments = (Element) rootDoc.selectSingleNode("//param");
		
		Iterator<Element> itr= elments.elementIterator();
		while (itr.hasNext()){
			Element m = itr.next();
			System.out.println( m.asXML());
		}
		for (Element ment : elments){
			String rname = ment.attributeValue("rname");
			System.out.println( ment.getName());
			System.out.println( ment.attributeValue("rmethod"));
			Element e= (Element) ment.selectSingleNode(rname);
			System.out.println( ment.asXML());
		}
		
		
		//System.out.println( rootDoc.asXML());
   
	}*/
}
