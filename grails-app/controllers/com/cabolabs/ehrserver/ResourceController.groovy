package com.cabolabs.ehrserver

import com.cabolabs.ehrserver.query.Query
import com.cabolabs.ehrserver.query.QueryShare
import com.cabolabs.security.Organization

class ResourceController {
   
   def resourceService
   
   // TODO: verify permissions of the user over the query & organizations
   
   def shareQuery(String uid)
   {
      def query = Query.findByUid(uid)
      def shares = QueryShare.findAllByQuery(query)
      render view:'/query/share', model:[query: query, organizations: shares.organization]
   }
   
   def saveSharesQuery()
   {
      Query query = params.query
      
      // delete all shares
      resourceService.cleanSharesQuery(query)
      
      // share with selected orgs
      def organization
      def orgUids = params.list('organizationUid')
      orgUids.each { organizationUid ->
         organization = Organization.findByUid(organizationUid)
         resourceService.shareQuery(query, organization)
      }
      redirect action:'shareQuery', params:[uid:query.uid]
   }
   
}