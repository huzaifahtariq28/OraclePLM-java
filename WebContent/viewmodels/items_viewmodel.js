define([ "binder" ], function(_binder) {
	var binder = _binder;
	
	function actionVM() {
		var self = this;
		var temp;
		
		self.items = ko.observableArray();
		
		self.newItemName = ko.observable();
		self.newItemClass = ko.observable();
		self.newItemDescription = ko.observable();
		
		//self.itemId = ko.observable();
		self.itemName = ko.observable();
		self.itemClass = ko.observable();
		self.itemDescription = ko.observable();
		
		self.onLoad = function() {
			System.sendPostRequest("LoadItem", null, function(response) {
		
				if (response.status == "success") {
					console.log("Passed");
					self.items(response.Items_data);
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
				//location.href = "http://localhost:8080/TemplateProject/#items";
				//location.reload(true);
			}
			
			window.onclick = function(event) {
		  		if (event.target == createModal) {
		    		createModal.style.display = "none";
				}
			}
		}
		
		self.createItems = function() {
			var req = new Object();
			req.itemName = self.itemName();
			req.itemClass = self.itemClass();
			req.itemDescription = self.itemDescription();
	
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
			
			System.sendPostRequest("CreateItem", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					createModal.style.display = "none";
					location.href = "http://localhost:8080/TemplateProject/#items";
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
			
			temp = contact.itemId;
			req.itemId = temp;
			
			self.newItemName(contact.itemName);
			self.newItemClass(contact.itemClass);
			self.newItemDescription(contact.itemDescription);
			
			closeEdit.onclick = function() {
				editModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == editModal) {
				    editModal.style.display = "none";
				}
			}
		}
		
		self.editItems = function() {
			req.itemName = self.newItemName();
			req.itemClass = self.newItemClass();
			req.itemDescription = self.newItemDescription();
					
			req = "params=" + encodeURIComponent(JSON.stringify(req));		
			console.log(req);
					
			System.sendPostRequest("EditItem", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#items"
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
			
			temp = contact.itemId;
			req.itemId = temp;
			
			closeDelete.onclick = function() {
				deleteModal.style.display = "none";
			}
			
			window.onclick = function(event) {
				if (event.target == deleteModal) {
				    deleteModal.style.display = "none";
				}
			}
		}
		
		self.deleteItem = function() { 
			req = "params=" + encodeURIComponent(JSON.stringify(req));
			console.log(req);
						
			System.sendPostRequest("DeleteItem", req, function(response) {
				if (response.status == "success") {
					console.log("Sent");
					location.href = "http://localhost:8080/TemplateProject/#items";
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
				
		self.listBOM = function(contact) {
			temp = contact.itemId;
			location.href = "http://localhost:8080/TemplateProject/#bom?itemId="+temp;
		}
			
		self.listAML = function(contact) {
			temp = contact.itemId;
			location.href = "http://localhost:8080/TemplateProject/#aml?itemId="+temp;
		}
			
		self.listAttachments = function(contact) {
			temp = contact.itemId;
			location.href = "http://localhost:8080/TemplateProject/#attachments?itemId="+temp;
		}
		
		self.refreshItems = function() {
			location.reload(true);
		}
				
		self.cloudData = function() {
			System.sendPostRequest("LoadData", null, function(response) {
				if (response.status == "success") {
					console.log("Passed");
					location.href = "http://localhost:8080/TemplateProject/#items";
					location.reload(true);
				}
				else {
					var message = "Error: ";
					console.log("Failed");
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Fetching Data From Cloud");
		}		
	}
	
	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
	
