package br.com.systemsgs.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.json.JSONObject;

public class AuthenticationPixConfiguration {
	
	/*Por Segurança o Client não é verdadeiro, tem que gerar la no GerenciaNet.com*/
	private final String client_id = "Clientid.548744785er1SADD45e78r121a4e87ddd";
	
	/*Por Segurança a Senha será outra*/
	private final String client_secret = "suasenhaaqui";
	
	private final String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));
	

	public String geraToken() {
		String access_token="";
		try {
			//Aqui será o Diretório onde vai ficar o certificado, precisa dele para podermos Autenticar
	        System.setProperty("javax.net.ssl.keyStore", "certificado.p12"); 
	        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	       
	        /*Em Ambiente de Produção*/
	        URL url = new URL ("https://api-pix.gerencianet.com.br/oauth/token");           
	        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Authorization", "Basic "+ basicAuth);
	        conn.setSSLSocketFactory(sslsocketfactory);
	        String input = "{\"grant_type\": \"client_credentials\"}";
	       
	        OutputStream os = conn.getOutputStream();
	        os.write(input.getBytes());
	        os.flush();   

	        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
	        BufferedReader br = new BufferedReader(reader);

	        String response;
	        StringBuilder responseBuilder = new StringBuilder();
	        while ((response = br.readLine()) != null) {
	          responseBuilder.append(response);
	        }
	        try {
				JSONObject jsonObject = new JSONObject(responseBuilder.toString());
				access_token = jsonObject.getString("access_token");
			} catch (Exception e) {
				System.out.println("Erro: "+ responseBuilder);
				e.printStackTrace();
			}
	        conn.disconnect();
		} catch (Exception e) {
			System.out.println("Ops! a Autenticação Falhou! ");
			e.printStackTrace();
		}
        
		return access_token;
	}

}
