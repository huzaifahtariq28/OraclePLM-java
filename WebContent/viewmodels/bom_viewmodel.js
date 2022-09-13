define([ "binder" ], function(_binder) {
	var binder = _binder;
	
	function actionVM() {
		var self = this;
		var temp ;
		
		self.boms = ko.observableArray();
		
		self.newBomName = ko.observable();
		self.newBomDescription = ko.observable();
		
		self.bomId = ko.observable();
		self.itemId = ko.observable();
		self.bomName = ko.observable();
		self.bomDescription = ko.observable();
		
		self.onLoad = function() {
			self.ItemID = System.getParameterByName("itemId");
			temp = self.ItemID;
			
			var req = new Object();
			req.itemId = temp;
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("LoadBOM", req, function(response) {
				if (response.status == "success") {
					console.log("Passed");
					self.boms(response.BOM_data);
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
		
		self.createBOM = function() {
			self.itemId = System.getParameterByName("itemId");
			temp = self.itemId;
	
			var req = new Object();
			req.itemId = temp;
			req.bomName = self.bomName();
			req.bomDescription = self.bomDescription();
	
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("CreateBOM", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					createModal.style.display = "none";
					location.href = "http://localhost:8080/TemplateProject/#bom?itemId="+temp;
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
			
			temp = contact.bomId;
			req.bomId = temp;
			
			self.newBomName(contact.bomName);
			self.newBomDescription(contact.bomDescription);
			
			closeEdit.onclick = function() {
				editModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == editModal) {
				    editModal.style.display = "none";
				}
			}
		}
		
		self.editBOM = function() {
			self.itemId = System.getParameterByName("itemId");
			temp = self.itemId;
			req.bomName = self.newBomName();
			req.bomDescription = self.newBomDescription();
					
			req = "params=" + encodeURIComponent(JSON.stringify(req));		
			console.log(req);
					
			System.sendPostRequest("EditBOM", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#bom?itemId="+temp;
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
			
			temp = contact.bomId;
			req.bomId = temp;
			
			closeDelete.onclick = function() {
				deleteModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == deleteModal) {
				    deleteModal.style.display = "none";
				}
			}
		}
		
		self.deleteBOM = function() { 
			self.itemId = System.getParameterByName("itemId");
			tempId = self.itemId;
			
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
						
			System.sendPostRequest("DeleteBOM", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#bom?itemId="+tempId;
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
		
		self.refreshBOM = function() {
			location.reload(true);
		}
	}
		
	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
	
