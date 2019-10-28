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

package com.cabolabs.ehrserver.ehr.clinical_documents

import org.springframework.dao.DataIntegrityViolationException
import com.cabolabs.ehrserver.ehr.clinical_documents.CompositionIndex
import grails.util.Holders

class CompositionIndexController {

   def configurationService

   def config = Holders.config.app

   def index()
   {
      // this is only accessable by admins
      params.max = configurationService.getValue('ehrserver.console.lists.max_items')
      [compositionIndexInstanceList: CompositionIndex.list(params), total: CompositionIndex.count()]
   }

   def show(Long id)
   {
      def compositionIndexInstance = CompositionIndex.get(id)
      if (!compositionIndexInstance)
      {
         flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositionIndex.label', default: 'CompositionIndex'), id])
         redirect(action: "index")
         return
      }

      [compositionIndexInstance: compositionIndexInstance]
   }
}
