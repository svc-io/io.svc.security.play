package io.svc.security.play

import io.svc.security.authentication._
import scalaz.Validation
import play.api.mvc._
import io.svc.security.std._
import io.svc.security.http
import io.svc.security.http.CredentialsExtractionFailure
import io.svc.security.std.UsernamePasswordCredentials
import play.api.mvc.Request

/**
 * @author Rintcius Blok
 */
object authentication {

  class PlayBasicAuthenticationCredentialsExtractor[A] extends CredentialsExtractor[Request[A], UsernamePasswordCredentials, CredentialsExtractionFailure] {
    override def extractCredentials(request: Request[A]): Validation[CredentialsExtractionFailure, UsernamePasswordCredentials] = {

      val authHeader = request.headers.get("AUTHORIZATION")

      http.extractCredentialsBasicAuthentication(authHeader)
    }
  }

  def authFailureHandler[A](result: Result) = {
    new AuthenticationFailureHandler[Request[A], AuthenticationFailure, Result] {
      override def onAuthenticationFailure(req: Request[A], f: AuthenticationFailure): Result = result
    }
  }

  trait PlayAuth[A, User] extends Authentication[Request[A], Result, User, AuthenticationFailure]
}