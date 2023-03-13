package com.bobodroid.balancegame.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobodroid.balancegame.MainActivity
import com.bobodroid.balancegame.TAG
import com.bobodroid.balancegame.viewmodels.dataViewModels.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.UUID.randomUUID
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class GameViewModel @Inject constructor(
    private val userIdRepository: UserIdRepository): ViewModel() {

    val db = Firebase.firestore

    val localId = MutableStateFlow("")

    // 저장된 게임 아이템
    private val _gameItemFlow = MutableStateFlow<List<GameItem>>(emptyList())

    val gameItemFlow = _gameItemFlow.asStateFlow()

    val gameItemCheckFlow = MutableStateFlow(false)
    fun gameItemEmptyCheck() {
       val result = if (_gameItemFlow.value == emptyList<GameItem>()) false else true
        viewModelScope.launch { gameItemCheckFlow.emit(result) }
    }
    // 게임 아이템 종류 선택

    private val _kindCurrentGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    // 랜덤아이템

    private val _currentRandomGameItem =
        MutableStateFlow<GameItem>(GameItem(randomUUID().toString(), "","", "",true ,0 ,ItemKind.FOOD))

    val currentRandomGameItem: StateFlow<GameItem> = _currentRandomGameItem.asStateFlow()


    // 사용한 게임 아이템

    private val _usedGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    private val _matchTextItem = MutableStateFlow<List<Compatibility>>(emptyList())

    val matchTextItem = _matchTextItem.asStateFlow()


    // 게임  ui 관리

    val usedGameItem = _usedGameItem.asStateFlow()

    val singleGameState = MutableStateFlow(false)

    val singleGameStage = MutableStateFlow(1)

    val togetherGameState = MutableStateFlow(false)

    val togetherGameStage = MutableStateFlow(1)

    val selectedKindItem = MutableStateFlow(ItemKind.FOOD)

    val isPlayGame = MutableStateFlow(true)

    val stageResultValue = MutableStateFlow(0)

    val saveName = MutableStateFlow("")

    private val _userSaveGameItem = MutableStateFlow<List<UserSaveGame>>(emptyList())

    val userSaveGameItem = _userSaveGameItem.asStateFlow()

    val gameCode = MutableStateFlow("")

    //질문 만들기

    val makeFirstGameItem = MutableStateFlow("")

    val makeSecondGameItem = MutableStateFlow("")

    val makeSelectedKindItem = MutableStateFlow(ItemKind.FOOD)

    val makerName = MutableStateFlow("")

    val privateFlow = MutableStateFlow(false)

    private val _userGameItemFlow = MutableStateFlow<List<GameItem>>(emptyList())

    val userGameItemFlow = _userGameItemFlow.asStateFlow()


    fun makeGameItem(){

        // 해시맵 만들기
        val newGameItem = GameItem(
            randomUUID().toString(),
            makerName.value,
            makeFirstGameItem.value,
            makeSecondGameItem.value,
            privateFlow.value ,
            0,
            makeSelectedKindItem.value)

        // Add a new document with a generated ID
        db.collection("gameitems")
            .document("${newGameItem.id}")
            .set(newGameItem.asHasMap())
            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val addedGameItems = _allGameItemFlow.value.toMutableList()
                val addedUserGameItems = _userGameItemFlow.value.toMutableList()
                addedGameItems.add(0, newGameItem)
                addedUserGameItems.add(0, newGameItem)
                viewModelScope.launch {
                    _allGameItemFlow.emit(addedGameItems)
                    _userGameItemFlow.emit(addedUserGameItems)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                }
            }


    val _allGameItemFlow = MutableStateFlow<List<GameItem>>(emptyList())

    val allGameItemFlow = _allGameItemFlow.asStateFlow()


    fun userQuestionLoad() {
        viewModelScope.launch {
            val filterUserItem = allGameItemFlow.value.filter { it.makerName == makerName.value }

            _userGameItemFlow.emit(filterUserItem)
        }
    }

    val mainGameItemLoadSuccessFlow = MutableStateFlow(false)

    // 게임 아이템 로드
    fun fetchAllGameItems(){
        db.collection("gameitems")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    mainGameItemLoadSuccessFlow.emit(true)
                    val fetchedgameItems = result.toObjects(GameItem::class.java)

                    val fetchedGameitems =  result.map { GameItem(it) }


                    val filterItems = fetchedgameItems.filter { it.private == true }





                    _allGameItemFlow.emit(fetchedgameItems)

                    _gameItemFlow.emit(filterItems)




                    Log.d(TAG, "게임 아이템 로드")

                }
            }
            .addOnFailureListener { exception ->
                Log.w(MainActivity.TAG, "Error getting documents.", exception)
            }
    }

    val id = "[d467319f-2706-40a5-8360-4e5415919c3b]"

    // 궁합 테스트를 위한 유저게임 저장시킨 것 로드
    fun fetchAllSaveItems() {
        db.collection("saveGameItems")
            .whereEqualTo("id", localId.value)
            .get()
            .addOnSuccessListener{ result ->
                viewModelScope.launch {
                    val fetchedSaveItem = result.toObjects(UserSaveGame::class.java)

                    Log.d(TAG, "로컬아이디 ${localId.value}")
                    Log.d(TAG, "저장값 불러오기 ${fetchedSaveItem}")
                    _userSaveGameItem.emit(fetchedSaveItem)
                }

            }
    }


    // 서버에 유저가 저장 시킨 게임 리스트 게임코드로 불러오기

    val codeMatchingGameItem = MutableStateFlow<List<UserSaveGame>>(emptyList())


    val success = codeMatchingGameItem.filterNot { it.isEmpty() }.combine(gameCode.filterNot { it.isEmpty() }) { gameItem, gameCode ->

        gameItem.map { it.GameCode == gameCode }.last() }


    fun fetchMatchItem() {
        db.collection("saveGameItems")
            .whereEqualTo("gameCode", gameCode.value)
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val fetchedGameItems = result.toObjects(UserSaveGame::class.java)

                    codeMatchingGameItem.emit(fetchedGameItems)

                    Log.d(TAG, "게임코드 불러오기 성공")

                    successLoadFlow.emit(true)
                    Log.d(TAG, "매칭코드 게임 $fetchedGameItems")
                    gameCode.emit("")

                }
            }
            .addOnFailureListener { exception ->
                viewModelScope.launch {
//                    successLoadFlow.emit(true)

                }

                Log.w(MainActivity.TAG, "Error getting documents.", exception)
            }
    }


    val compatibilitySaveName = MutableStateFlow("")

    val zeroFlow = MutableStateFlow("")
    val oneFlow = MutableStateFlow("")
    val twoFlow = MutableStateFlow("")
    val threeFlow = MutableStateFlow("")
    val fourFlow = MutableStateFlow("")
    val fiveFlow = MutableStateFlow("")
    val sixFlow = MutableStateFlow("")
    val sevenFlow = MutableStateFlow("")
    val eightFlow = MutableStateFlow("")
    val nineFlow = MutableStateFlow("")
    val tenFlow = MutableStateFlow("")


    val _startSaveGameValue = MutableStateFlow<UserSaveGame>(UserSaveGame(
        randomUUID().toString(),
        randomUUID().toString(),
        "",
        emptyList()))

    val startSaveGameValue = _startSaveGameValue.replayCache

    val matchingGameItemState: StateFlow<List<UserSaveGame>> = codeMatchingGameItem.stateIn(viewModelScope, SharingStarted.Eagerly, startSaveGameValue)


    private val _togetherGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val togetherGameItem = _togetherGameItem.asStateFlow()

    val _currentTogetherGameItem =  MutableStateFlow<GameItem>(GameItem(randomUUID().toString(), "", "00", "00", true ,0, ItemKind.FOOD))

    val currentTogetherGameItem = _currentTogetherGameItem.asStateFlow()


    val successLoadFlow = MutableStateFlow(false)



    //관리자 모드

    val editingGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val editingGameItemId = MutableStateFlow("")

    val editIsLoading = MutableStateFlow(false)

    fun editPrivateGameItem(gameItem: GameItem ,private: Boolean) {

        val editingGameItem = GameItem(gameItem.id, gameItem.makerName, gameItem.firstItem, gameItem.secondItem, true, gameItem.selectItem, gameItem.itemKind)



        val editedGameItem = editingGameItem.asHasMap()

        db.collection("gameitems")
            .whereEqualTo("id", "fee4ed89-747f-4068-9fb8-3fe5e5f15a61")
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "수정 성공")

            }.addOnFailureListener {
                Log.d(TAG, "수정 실패")
            }

    }

    init {



//        allRemoveLocalId()


//        fetchAllGameItems()

        viewModelScope.launch(Dispatchers.IO) {
            userIdRepository.getAllUserId().distinctUntilChanged()
                .collect(){userId ->
                    if(userId.isNullOrEmpty()) {
                        Log.d(TAG, "로컬 유저아이디 없음") }
                    else {
                        val loadId = userId.map { it.id }
                        localId.emit(loadId.toString())
                        Log.d(TAG, "${localId.value}")
                    }
                }
        }

        viewModelScope.launch {
            delay(1000)
                fetchAllSaveItems()
                Log.d(TAG, "불러오기 실행")
        }



        viewModelScope.launch {
            delay(2000)
            if(mainGameItemLoadSuccessFlow.value == true){
            singleGameState.collectLatest {
                selectedKindItem
                    .collectLatest {
                        val kindFilterItem = getKindFilterItem(_gameItemFlow.value)
                        _kindCurrentGameItem.emit(kindFilterItem)
                        singleGameStage
                            .collectLatest {
                                val (shirnkedList, randomGameItem) = getRandomGameItem(_kindCurrentGameItem.value)
                                _kindCurrentGameItem.emit(shirnkedList)
                                _currentRandomGameItem.emit(randomGameItem)
                            }
                    }

            }

        }
        }

    }
    fun startTogetherGame(){
        viewModelScope.launch {
                val (shirnkedList, togetherRandomGameItem) = startTogetherGame(matchingGameItemState)
                _togetherGameItem.emit(shirnkedList)
                _currentTogetherGameItem.emit(togetherRandomGameItem)

        }
    }

    fun getKindFilterItem(gameItemList: List<GameItem>): List<GameItem> {
        var tempGameItemList = gameItemList
        val kindGameItem = tempGameItemList.filter { it.itemKind == selectedKindItem.value }
        return kindGameItem
    }

    fun getRandomGameItem(gameItemList: List<GameItem>): Pair<List<GameItem>, GameItem> {

        var tempGameItemList = gameItemList

        val randomGameItem = gameItemList.random()

        val finalGameItemList = tempGameItemList.filter { it.id != randomGameItem.id }

        return Pair(finalGameItemList, randomGameItem)
    }


    fun startTogetherGame(saveItemList: StateFlow<List<UserSaveGame>>): Pair<List<GameItem>, GameItem> {

        val tempGameItemList = saveItemList.value.flatMap { it.gameItems }

        val saveItemRandom = saveItemList.value.flatMap { it.gameItems }.random()

        val finalGameItemList = tempGameItemList.filter { it.id != saveItemRandom.id }

        return Pair(finalGameItemList, saveItemRandom)
    }


    fun gameItemReset() {
        viewModelScope.launch {
            singleGameState.value = true
            singleGameStage.emit(1)
            saveName.emit("")
        }
    }

    fun moveToNextStage() {
        viewModelScope.launch {
            if (singleGameStage.value != 10)
                singleGameStage.emit(singleGameStage.value.plus(1))
        }
    }

    fun togetherMoveToNextStage() {
        viewModelScope.launch {
            if (togetherGameStage.value != 10)
                togetherGameStage.emit(togetherGameStage.value.plus(1))
        }
    }

    fun togetherGameItemReset() {
        viewModelScope.launch {
            togetherGameState.value = true
            togetherGameStage.emit(1)
        }
    }


    fun usedGameItem(gameItem: GameItem) {
        viewModelScope.launch {
            val usedGameItem = _usedGameItem.value

            val items = usedGameItem.toMutableList().apply {
                add(GameItem(gameItem.id, gameItem.makerName, gameItem.firstItem, gameItem.secondItem, true ,stageResultValue.value , gameItem.itemKind))
            }.toList()
            _usedGameItem.value = items

        }
    }


    fun saveCompatibility(){

        // 해시맵 만들기
        val newSaveItem = Compatibility(
            randomUUID().toString(),
            compatibilitySaveName.value,
            zeroFlow.value,
            oneFlow.value,
            twoFlow.value,
            threeFlow.value,
            fourFlow.value,
            fiveFlow.value,
            sixFlow.value,
            sevenFlow.value,
            eightFlow.value,
            nineFlow.value,
            tenFlow.value)

        db.collection("saveCompatibility")
            .add(newSaveItem.asHasMap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val addedGameItems = _matchTextItem.value.toMutableList()
                addedGameItems.add(0, newSaveItem)
                viewModelScope.launch {
                    _matchTextItem.emit(addedGameItems)
                }
            }
    }


    fun saveCompatibilityAllLoad(){
        db.collection("saveCompatibility")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {
                    val fetchedCompatibility = result.toObjects(Compatibility::class.java)
                    _matchTextItem.emit(fetchedCompatibility)

                    Log.d(TAG, "궁합 문구 로드 성공")

                }
            }
            .addOnFailureListener { exception ->
                Log.w(MainActivity.TAG, "Error getting documents.", exception)
            }
    }




    fun saveGameItem(){

        // 해시맵 만들기
        val newSaveItem = UserSaveGame(
            localId.value,
            randomUUID().toString(),
            saveName.value,
            usedGameItem.value)

        db.collection("saveGameItems")
            .add(newSaveItem.asHasMap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val addedGameItems = _userSaveGameItem.value.toMutableList()
                addedGameItems.add(0, newSaveItem)
                viewModelScope.launch {
                    _userSaveGameItem.emit(addedGameItems)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    fun allRemoveLocalId() {
        viewModelScope.launch {
            userIdRepository.deleteAllUserId(userId = SaveId())
        }
    }



   private val _compatibilityValue = MutableStateFlow<List<MatchSelect>>(emptyList())

    val compatibilityFlow = _compatibilityValue.asStateFlow()

    val compatibilityListNumber = MutableStateFlow(0)

    val firstUserSelect = MutableStateFlow(0)

    val secondUserSelect = MutableStateFlow(0)

    val compatibilityPercent = MutableStateFlow(0)

    val compatibilityResultSum = MutableStateFlow(0)

    val compatibilityText = MutableStateFlow("")



    fun actionSelect() {
        viewModelScope.launch {

            val firstItem = _currentTogetherGameItem.value.firstItem

            val secondItem = _currentTogetherGameItem.value.secondItem

            val firstUser = if(firstUserSelect.value == 1) firstItem else secondItem

            val secondUser = if(secondUserSelect.value == 1) firstItem else secondItem

            val compatibilityList = _compatibilityValue.value

            val compatibilityResult = if(firstUserSelect.value == secondUserSelect.value) true else false

            val compatibilityCalculate = if(firstUserSelect.value == secondUserSelect.value) 1 else 0

            compatibilityResultSum.value += compatibilityCalculate

            val items = compatibilityList.toMutableList().apply {
                add(MatchSelect(compatibilityListNumber.value, firstUser!!, secondUser!!, compatibilityResult)) }

            _compatibilityValue.value = items

            }
        }


    fun lastResult() {
        viewModelScope.launch {
            compatibilityPercent.emit(finalCompatibility())
        }
    }

    fun compatibilityMatch() {
        viewModelScope.launch {
            val compatibilityTextItem = _matchTextItem.value

            val textRandom = compatibilityTextItem.random()

            val percentText = when(compatibilityPercent.value) {

                0 -> {textRandom.zero }
                10 -> {textRandom.one }
                20 -> {textRandom.two }
                30 -> {textRandom.three }
                40 -> {textRandom.four }
                50 -> {textRandom.five }
                60 -> {textRandom.six }
                70 -> {textRandom.seven}
                80 -> {textRandom.eight}
                90 -> {textRandom.nine}
                100 -> {textRandom.ten }

                else -> ""
            }

            compatibilityText.emit(percentText)

        }
    }



    private fun finalCompatibility() = compatibilityResultSum.value * 10

}



data class MatchSelect(val listNumber: Int, val firstUserSelect: String, val SecondUserSelect: String, val selectResult: Boolean)








