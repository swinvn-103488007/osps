const PostItem = {
    props: {
      delete_post: {
        type: Function,
        required: true
      },
      post_data: {
        type: Object,
        required: true
      },
      user: {
        type: [Object, null],
        required: true
      }
    },
    data() {
      return {
          post: {
            postId: this.post_data.post_id,
            friendId: this.post_data.friend_id,
            profileName: this.post_data.profile_name,
            friendEmail: this.post_data.friend_email,
            liked: parseInt(this.post_data.liked),
            likeCount: parseInt(this.post_data.like_count),
            commentCount: this.post_data.comment_count,
            content: this.post_data.content,
            comments: [],
            editing: false,
            editingContent: this.post_data.content,
          },
          authenticatedUser: this.user,
          commentContent: ''
      }
    },
    computed: {
      commentPlaceholder() {
        return 'Leave a comment on ' + this.post.profileName + '\'s post...'
      }
    },
    template: `
    <div class="post-details">
      <div class="post-header">
        <div class="d-flex align-items-center w-100 justify-content-between flex-wrap">
          <h3>{{post.profileName}}</h3>
          <div v-if="this.post.friendId == this.authenticatedUser.id" class="d-flex">
            <button class="button" @click="()=>this.edit()">{{this.post.editing ? "Save" : "Edit"}}</button>
            <button class="button" v-if="this.post.editing" @click="()=>{this.post.editing = false}">Cancel</button>
            <button class="button text-danger" @click="()=>this.delete_post(this.post.postId)">Delete</button>
          </div>
        </div>
      </div>
      <p v-if="!this.post.editing">{{post.content}}</p>
      <textarea v-else class="post-edit" rows="3" v-model="post.editingContent"></textarea>
      <div class="post-action">
        <button class="button" :class="post.liked && 'liked'" @click="()=>this.like()">{{post.likeCount}} Like{{post.likeCount > 1 ? "s" : ""}}</button>
        <button class="button" @click="()=>this.commentClick()" data-toggle="collapse" :data-target="'#post-collapse' + post.postId" aria-expanded="false" :aria-controls="'post-collapse' + post.postId">{{post.commentCount}} Comment{{post.commentCount > 1 ? "s" : ""}}</button>
      </div>
      <div class="post-comment collapse" :id="'post-collapse' + post.postId">
        <form class="compose-comment" @submit.prevent="()=>comment()">
          <input v-model="commentContent" :placeholder="commentPlaceholder"/>
          <button type="submit" @click.prevent="()=>comment()">Comment</button>
        </form>
        <div class="comment-list">
          <div v-if="post.comments && post.comments.length > 0" v-for="comment in post.comments" :key="comment.comment_id" class="comment">
            <p class="comment-author">{{comment.profile_name}}</p>
            <p class="comment-content">{{comment.content}}</p>
          </div>
          <p v-else class="subtle-text">No comment yet</p>
        </div>
      </div>
    </div>
    `,
    methods: {
      like() {
        console.log(this.post.liked)
        if(this.post.liked) {
          this.post.liked = 0
          this.post.likeCount -= 1
        } else {
          this.post.liked = 1
          this.post.likeCount += 1
        }

        let payload = {
          friend_id: this.authenticatedUser.id,
          post_id: this.post.postId
        }
  
        const requestOptions = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(payload)
        };
    
        fetch('resources/like.php/', requestOptions)
          .then(response =>{
            //turning the response into the usable data
            return response.json();
          })
          .then(data => {
            if(!data?.data || data.message) {
              this.msg = data.message
            } else {
              this.post.liked = parseInt(data.data.liked)
              this.post.likeCount = parseInt(data.data.like_count)
            }
          })
          .catch(error => {
            this.msg = error;
          });
      },
      commentClick() {

        if(this.commentContent) {
          this.pageNum = 0
          this.post.comments = []
        }
  
        let payload = {
          id: this.authenticatedUser.id,
          post_id: this.post.postId,
          page_num: this.pageNum, 
          comment_content: this.commentContent
        }
  
        const requestOptions = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(payload)
        };
    
        fetch('resources/comment.php/', requestOptions)
          .then(response =>{
            //turning the response into the usable data
            return response.json();
          })
          .then(data => {
            if(data.message) {
              this.msg = data.message
            } else {
              this.pageNum = data.page_num
              this.total = data.total
              this.maxPage = data.max_page
              this.post.commentCount = data.total
              this.post.comments = [ ...data.comment_list]
              this.commentContent = ''
            }
          })
          .catch(error => {
            this.msg = error;
          });
      },
      edit() {
        if(this.post.editing) {
          let payload = {
            id: this.authenticatedUser.id,
            post_id: this.post.postId,
            edit_content: this.post.editingContent
          }
    
          const requestOptions = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
          };
      
          fetch('resources/postedit.php/', requestOptions)
            .then(response =>{
              //turning the response into the usable data
              return response.json();
            })
            .then(data => {
              if(data.message) {
                this.msg = data.message
              } else {
                this.post.content = data.new_content
                this.post.editingContent = ''
              }
            })
            .catch(error => {
              this.msg = error;
            });

            this.post.editing = false
        } else {
          this.post.editing = true
        }
      },
      comment() {
        this.commentClick()
      },
    } 
  }
  