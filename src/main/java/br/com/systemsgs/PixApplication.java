package br.com.systemsgs;

import br.com.systemsgs.config.AuthenticationPixConfiguration;
import br.com.systemsgs.config.ChangerPix;
import br.com.systemsgs.config.GenerationQrCode;

public class PixApplication 
{
    public static void main( String[] args )
    {
    	AuthenticationPixConfiguration authProd = new AuthenticationPixConfiguration();
    	String access_token;
    	
    	ChangerPix cobranca = new ChangerPix();
    	String resultCob="";
    	int idCob=0;
        
    	GenerationQrCode enerationQrCode = new GenerationQrCode();
    	String resultLoc;
    	String qrCode="";
    	String image="";
    	String imageName;
        
    	/*Autentica*/
    	access_token = authProd.geraToken();
    	System.out.println("access_token = "+access_token);
    	
    	/*Cria uma Cobrança autenticada*/
    	resultCob = cobranca.doCob(access_token);
    	idCob = cobranca.getIdCob(resultCob);
    	System.out.println("idCobranca = "+idCob);
    	
    	/*Emissão de um QRCode Location*/
    	resultLoc = enerationQrCode.genQrCode(idCob, access_token);
    	qrCode = enerationQrCode.getQrCode(resultLoc);
    	System.out.println("qrCode = "+qrCode);
    	
    	/*Exibe e Salva a Imagem QRCode*/
    	image = enerationQrCode.getImage(resultLoc);
    	System.out.println("image = "+image);
    	imageName=enerationQrCode.saveImage(image);
    	enerationQrCode.showImage(imageName);
    }
}
