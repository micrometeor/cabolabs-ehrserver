/*
 * Copyright 2011-2017 CaboLabs Health Informatics
 *
 * The EHRServer was designed and developed by Pablo Pazos Gutierrez <pablo.pazos@cabolabs.com> at CaboLabs Health Informatics (www.cabolabs.com).
 *
 * You can't remove this notice from the source code, you can't remove the "Powered by CaboLabs" from the UI, you can't remove this notice from the window that appears then the "Powered by CaboLabs" link is clicked.
 *
 * Any modifications to the provided source code can be stated below this notice.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cabolabs.security

import grails.transaction.Transactional
import org.springframework.security.core.authority.AuthorityUtils
import grails.plugin.springsecurity.SpringSecurityUtils

@Transactional
class UserService {

   def notificationService
   def springSecurityService
   
   def getByUsername(String username)
   {
      def u = User.findByUsername(username) // can be null
      
      return u
   }
    
   def getUserAuthorities(User user, Organization org)
   {
      def aus = user.getAuthorities(org) // Set<Role>
      def authstr = aus.authority // List<String> with role names
      
      // http://docs.spring.io/autorepo/docs/spring-security/3.2.1.RELEASE/apidocs/org/springframework/security/core/authority/AuthorityUtils.html
      return AuthorityUtils.createAuthorityList(authstr as String[]) // List<GrantedAuthority>
   }
   
   /*
   def saveAndNotify(User userInstance, params)
   {
      if (!userInstance.password)
      {
         userInstance.enabled = false
         userInstance.setPasswordToken()
      }
            
      userInstance.save(failOnError:true)
            
      // TODO: UserRole ORG_* needs a reference to the org, since the user
      //      can be ORG_ADMIN in one org and ORG_STAFF in another org.

      // Add selected roles
      def roles = params.list('role')
            
      roles.each { authority ->
         
         UserRole.create( userInstance, (Role.findByAuthority(authority)), true )
      }
      
      // TODO: schedule emails
      // token to create the URL for the email is in the userInstance
      notificationService.sendUserCreatedEmail( userInstance.email, [userInstance] )
   }
   */
}
