package io.svc.security.play

import play.api.mvc.{Action, Result, Request, BodyParser}
import io.svc.security.play.authentication.PlayAuth

/**
 * @author Rintcius Blok
 */
object security {
  trait PlaySecurity[A, User] {

    val auth: PlayAuth[A, User]

    def securedAction(parser: BodyParser[A])(action: (Request[A], User) => Result): Action[A] = Action(parser) {
      req =>
        auth.authentication(action)(req)
    }

    //TODO how to get this working..
    //def securedAction(action: (Request[AnyContent], User) => Result): Action[A] = securedAction (BodyParsers.parse.anyContent) action
  }

}
