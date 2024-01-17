package com.ra.model.dto.request;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserLogin {
	@NotNull(message = "Không đuược null")
	@NotEmpty(message = "Không được rỗng")
	@NotBlank(message = "Không được để trống")
	@Size // dùng cho chuỗi
	@Length // dùng cho chuỗi
	@Min() // dùng cho số
	@Max() // dùng cho số
	@Pattern() // dùng pattern để validate
	private String username;
	
	@NotNull(message = "Không đuược null")
	@NotEmpty(message = "Không được rỗng")
	@NotBlank(message = "Không được để trống")
	private String password;
}
