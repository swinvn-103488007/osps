// Creating the VueRouter

const router = VueRouter.createRouter({
	history: VueRouter.createWebHashHistory(), 
	routes: [
	{
		path: '/',
		component: Login,
		name:"login",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/register',
		component: Register,
		name:"register",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/logout',
		name:"logout"
	},
	{
		path: '/availability',
		component: Availability,
		name:"availability",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/checkout',
		component: Checkout,
		name:"checkout",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/invoice',
		component: Invoice,
		name:"invoice",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/receipt',
		component: Receipt,
		name:"receipt",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/admin',
		component: Admin,
		name:"admin",
    	props: (route) => ({ user: route.params.user }),
	},
	{
		path: '/payment',
		component: Payment,
		name:"payment",
    	props: (route) => ({ user: route.params.user }),
	},
  ]
})

//create new vue instance  
const app = Vue.createApp({
  	data() {
	  	return{
			authenticatedUser: null,
			error:false,
			errorMsg:'',
  		}
  	},
   	mounted() {
        if(!this.authenticated) {
			this.$router.replace({ name: "login" });
		}
    },
	methods: {
		setAuthenticatedUser(user) {
		  	this.authenticatedUser = user;

		},
		logout(){
			$cookies.set('user', '')
		  	this.authenticatedUser = null;
			this.$router.replace({ name: "login" });
		}
	},  
    
});

app.component('app-nav', NavBar);
// app.component('post-item', PostItem);
app.use(router)

app.directive('nav-item', (el, binding) => {
    const route = router.currentRoute.value.name
	navItemEl = $(el)
	if(route === binding.value) {
		navItemEl.addClass('active')
	} else {
		navItemEl.removeClass('active')
	}
})

app.mount('#app')