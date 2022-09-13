define([ "binder" ], function(_binder) {
	var binder = _binder;
	
	function actionVM() {
		var self = this;
		var temp ;
		
		self.amls = ko.observableArray();
		
		self.newAmlName = ko.observable();
		self.newAmlDescription = ko.observable();
		
		self.amlId = ko.observable();
		self.amlId = ko.observable();
		self.amlName = ko.observable();
		self.amlDescription = ko.observable();
		
		self.onLoad = function() {
			self.ItemID = System.getParameterByName("itemId");
			temp = self.ItemID;
			
			var req = new Object();
			req.itemId = temp;
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("LoadAML", req, function(response) {
				if (response.status == "success") {
					console.log("Passed");
					self.amls(response.AML_data);
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
		
		self.createAML = function() {
			self.itemId = System.getParameterByName("itemId");
			temp = self.itemId;
	
			var req = new Object();
			req.itemId = temp;
			req.amlName = self.amlName();
			req.amlDescription = self.amlDescription();
	
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("CreateAML", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					createModal.style.display = "none";
					location.href = "http://localhost:8080/TemplateProject/#aml?itemId="+temp;
					location.reload(true);
				} else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Creating...");
		}
		
		var req = new Object();
		
		self.editModal = function(contact) {
			var editModal = document.getElementById("editModal");
			var closeEdit = document.getElementById("editClose");
			
			editModal.style.display = "block";
			
			temp = contact.amlId;
			req.amlId = temp;
			
			self.newAmlName(contact.amlName);
			self.newAmlDescription(contact.amlDescription);
			
			closeEdit.onclick = function() {
				editModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == editModal) {
				    editModal.style.display = "none";
				}
			}
		}
		
		self.editAML = function() {
			self.itemId = System.getParameterByName("itemId");
			temp = self.itemId;
			req.amlName = self.newAmlName();
			req.amlDescription = self.newAmlDescription();
					
			req = "params=" + encodeURIComponent(JSON.stringify(req));		
			console.log(req);
					
			System.sendPostRequest("EditAML", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#aml?itemId="+temp;
					location.reload(true);
				} else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Updating...");
		}
		
		var req = new Object();
		
		self.deleteModal = function(contact) {
			var deleteModal = document.getElementById("deleteModal");
			var closeDelete = document.getElementById("deleteClose");
			
			deleteModal.style.display = "block";
			
			temp = contact.amlId;
			req.amlId = temp;
			
			closeDelete.onclick = function() {
				deleteModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == deleteModal) {
				    deleteModal.style.display = "none";
				}
			}
		}
		
		self.deleteAML = function() { 
			self.itemId = System.getParameterByName("itemId");
			tempId = self.itemId;
			
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
						
			System.sendPostRequest("DeleteAML", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#aml?itemId="+tempId;
					location.reload(true);
				} else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Deleting...");
		}	
		
		self.refreshAML = function() {
			location.reload(true);
		}
	}
		
	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
	