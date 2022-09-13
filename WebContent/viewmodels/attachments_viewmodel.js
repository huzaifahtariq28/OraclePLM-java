define([ "binder" ], function(_binder) {
	var binder = _binder;
	
	function actionVM() {
		var self = this;
		var temp ;
	
		self.attachments = ko.observableArray();
		
		self.attachmentId = ko.observable();
		self.itemId = ko.observable();
		self.attachmentName = ko.observable();
		self.attachmentDescription = ko.observable();
		self.attachmentFile = ko.observable();
		
		self.onLoad = function() {
			self.ItemID = System.getParameterByName("itemId");
			temp = self.ItemID;
			self.itemId(temp);
			
			var req = new Object();
			req.itemId = temp;
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("LoadAttachment", req, function(response) {
				if (response.status == "success") {
					console.log("Passed");
					self.attachments(response.Attachment_data);
				} else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			});
		};
		
		self.createModal = function() {
			var createModal = document.getElementById("createModal");
			var closeCreate = document.getElementById("createClose");
			
			createModal.style.display = "block";
			
			closeCreate.onclick = function() {
		  		createModal.style.display = "none";
			}
			
			window.onclick = function(event) {
		  		if (event.target == createModal) {
		    		createModal.style.display = "none";
				}
			}
		}
		
		var req = new Object();
		
		self.deleteModal = function(contact) {
			var deleteModal = document.getElementById("deleteModal");
			var closeDelete = document.getElementById("deleteClose");
			
			deleteModal.style.display = "block";
			
			temp = contact.attachmentId;
			req.attachmentId = temp;
			
			closeDelete.onclick = function() {
				deleteModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == deleteModal) {
				    deleteModal.style.display = "none";
				}
			}
		}
		
		self.deleteAttachment = function(contact) { 
			tempId = System.getParameterByName("itemId");
						
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
						
			System.sendPostRequest("DeleteAttachment", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#attachments?itemId="+tempId;
					location.reload(true);		
				} else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Deleting...");
		};
		
		self.refreshAttachment = function() {
			location.reload(true);
		}
	}
		
	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});