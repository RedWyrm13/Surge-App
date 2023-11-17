import com.google.firebase.auth.FirebaseAuth

class AuthenticationManager {

    fun signUpUser(email: String, password: String, onComplete: (Boolean, String) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Sign-Up Successful")
                } else {
                    // Handle the error
                    task.exception?.message?.let {
                        onComplete(false, it)
                    }
                }
            }
    }
}
