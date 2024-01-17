package com.ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRegister {
	@NotNull(message = "Không đuược null")
	@NotEmpty(message = "Không được rỗng")
	@NotBlank(message = "Không được để trống")
	private String fullName;
	@NotNull(message = "Không đuược null")
	@NotEmpty(message = "Không được rỗng")
	@NotBlank(message = "Không được để trống")
	private String username;
	@NotNull(message = "Không đuược null")
	@NotEmpty(message = "Không được rỗng")
	@NotBlank(message = "Không được để trống")
	private String password;
	
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "không đúng định dạng")
	private String email;
	
	// front end
	// back end
	// database
}
