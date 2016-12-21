package oauth;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import repositories.UsersRepository;
import controller.AppException;
import entities.User;

public class OAuthHelper {

	private UsersRepository userRepository = new UsersRepository();
	
	public void Authenticate(HttpServletRequest request) throws AppException {
		try {
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request,
					ParameterStyle.HEADER);
			
			// Get the access token
			String accessToken = oauthRequest.getAccessToken();
			
			User user = userRepository.getCustomerByToken(accessToken);
			
			// Validate the access token
			String roles = user.getRoles();
			
			if (roles == null) {
				throw new AppException("Permission not enough", Response.Status.UNAUTHORIZED);
			}
			
			List<String> rolesList = Arrays.asList(roles.split(";"));
			if (!rolesList.contains("ADMIN")) {
				throw new AppException("Permission not enough", Response.Status.UNAUTHORIZED);
			}
			
		} catch (OAuthProblemException e) {
            // Check if the error code has been set
            String errorCode = e.getError();
            if (OAuthUtils.isEmpty(errorCode)) {
            	throw new AppException("Access invalid", Response.Status.UNAUTHORIZED);
            }
            
            throw new AppException("Access invalid: Error=" + e.getError() + " - Description=" + e.getDescription(), 
            		Response.Status.UNAUTHORIZED);
        } catch (OAuthSystemException e) {
          	throw new AppException("OAuth System Exception", Response.Status.UNAUTHORIZED);
		}

	}
}
