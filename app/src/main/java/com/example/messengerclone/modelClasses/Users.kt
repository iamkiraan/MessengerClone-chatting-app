package com.example.messengerclone.modelClasses

class Users {
    private var uid: String =""
    private var cover: String =""
    private var facebook: String =""
    private var profile: String =""
    private var search: String =""
    private var status: String =""
    private var username: String =""
    private var website: String =""
    constructor()

    constructor(
        uid: String,
        cover: String,
        facebook: String,
        profile: String,
        search: String,
        status: String,
        usernname: String,
        website: String
    ) {
        this.uid = uid
        this.cover = cover
        this.facebook = facebook
        this.profile = profile
        this.search = search
        this.status = status
        this.username = username
        this.website = website
    }//getter setter ko kaam ho
    fun getUID() : String {
        return uid
    }
    fun setUID(uid : String){
        this.uid = uid
    }
    fun getUsername() : String {
        return username
    }
    fun setUsername(username : String){
        this.username = username
    }
    fun getProfile() : String {
        return profile
    }
    fun setProfile(profile : String){
        this.profile = profile
    }
    fun getCover() : String {
        return cover
    }
    fun setCover(cover : String){
        this.cover = cover
    }
    fun getStatus() : String {
        return status
    }
    fun setStatus(status : String){
        this.status = status
    }
    fun getSearch() : String {
        return search
    }
    fun setSearch(search : String){
        this.search = search
    }
    fun getFacebook (): String {
        return facebook
    }
    fun setFacebook(facebook : String){
        this.facebook= facebook
    }
    fun getWebsite() : String {
        return website
    }
    fun setWebsite(website : String){
        this.website = website
    }


}