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
import com.bobodroid.balancegame.viewmodels.dataViewModels.GameItem
import com.bobodroid.balancegame.viewmodels.dataViewModels.ItemKind
import com.bobodroid.balancegame.viewmodels.dataViewModels.UserData
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import java.util.UUID.randomUUID
import kotlin.collections.HashMap

class GameViewModel: ViewModel() {

    val db = Firebase.firestore

    // 저장된 게임 아이템
    private val _gameItemFlow = MutableStateFlow<List<GameItem>>(emptyList())

    val gameItemFlow = _gameItemFlow.asStateFlow()

    // 게임 아이템 종류 선택

    private val _kindCurrentGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    // 랜덤아이템

    private val _currentRandomGameItem =
        MutableStateFlow<GameItem>(GameItem(randomUUID().toString(), "","00", "00", 0 ,ItemKind.FOOD))

    val currentRandomGameItem: StateFlow<GameItem> = _currentRandomGameItem.asStateFlow()


    // 사용한 게임 아이템

    private val _usedGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    private val _matchTextItem = MutableStateFlow<List<Compatibility>>(emptyList())


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

    val listNumber = MutableStateFlow(0)

    val gameCode = MutableStateFlow("")

    //질문 만들기

    val makeFirstGameItem = MutableStateFlow("")

    val makeSecondGameItem = MutableStateFlow("")

    val makeSelectedKindItem = MutableStateFlow(ItemKind.FOOD)

    val makerName = MutableStateFlow("관리자")



    fun makeGameItem(){

        // 해시맵 만들기
        val newGameItem = GameItem(randomUUID().toString(),makerName.value, makeFirstGameItem.value, makeSecondGameItem.value, 0, makeSelectedKindItem.value)

        // Add a new document with a generated ID
        db.collection("gameitems")
            .add(newGameItem.asHasMap())
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val addedGameItems = _gameItemFlow.value.toMutableList()
                addedGameItems.add(0, newGameItem)
                viewModelScope.launch {
                    _gameItemFlow.emit(addedGameItems)
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                }
            }


    private fun fetchAllGameItems(){
        db.collection("gameitems")
            .get()
            .addOnSuccessListener { result ->
                viewModelScope.launch {

                   val fetchedgameItems = result.toObjects(GameItem::class.java)
                    _gameItemFlow.emit(fetchedgameItems)

                    Log.d(TAG, "DocData ${result.documents}")

                }
            }
            .addOnFailureListener { exception ->
                Log.w(MainActivity.TAG, "Error getting documents.", exception)
            }
    }




    val item1 = GameItem(randomUUID().toString(), "보보", "짬뽕", "짜장",0, ItemKind.FOOD)
    val item2 = GameItem(randomUUID().toString(), "보보", "우동", "라면",0, ItemKind.FOOD)
    val item3 = GameItem(randomUUID().toString(), "보보", "딸기", "포도",0, ItemKind.FOOD)
    val item4 = GameItem(randomUUID().toString(), "보보", "수박", "레몬",0, ItemKind.FOOD)
    val item5 = GameItem(randomUUID().toString(), "보보", "찍먹", "부먹",0, ItemKind.FOOD)
    val item6 = GameItem(randomUUID().toString(), "보보", "초밥", "회",0, ItemKind.FOOD)
    val item7 = GameItem(randomUUID().toString(), "보보", "고기", "생선",0, ItemKind.FOOD)
    val item8 = GameItem(randomUUID().toString(), "보보", "콜라", "사이다",0, ItemKind.FOOD)
    val item9 = GameItem(randomUUID().toString(), "보보", "고구마", "감자",0, ItemKind.FOOD)
    val item10 = GameItem(randomUUID().toString(), "라민", "한우", "삼겹살",0, ItemKind.FOOD)
    val item11 = GameItem(randomUUID().toString(), "라민", "우주", "바다",0, ItemKind.TRAVEL)
    val item12 = GameItem(randomUUID().toString(), "라민", "강", "산",0, ItemKind.TRAVEL)
    val item13 = GameItem(randomUUID().toString(), "라민", "엄마", "아빠",0, ItemKind.ETC)
    val item14 = GameItem(randomUUID().toString(), "라민", "딸", "아들",0, ItemKind.ETC)
    val item15 = GameItem(randomUUID().toString(), "라민", "키스", "포옹",0, ItemKind.LOVE)


    val items = listOf(
        item1,
        item2,
        item3,
        item4,
        item5,
        item6,
        item7,
        item8,
        item9,
        item10,
        item11,
        item12,
        item13,
        item14,
        item15
    )

    val text1 = Compatibility(
        "이것 또한 천생연분! '반대가 끌리는 이유'라는 말도 있죠? 서로의 단점을 보완하고 감싸 안아주세요.",
        "10개 중 한 개만 맞다니 그건 초등학교 받아쓰기 점수 이후로 처음이네요. 서로 노력하세요.",
        "이제 알아가는 단계! 연애도 우정도 서로 알아가는 단계가 가장 설레는 시기입니다. 이 셀렘을 끝까지 기억해주세요.",
        "이제 탐색하는 단계! 서로의 취향이 뭔지 서로 관찰하며 배려하는 인연이 되시길 바랍니다.",
        "너를 안다고 생각한 내 생각이 착각이었어! 우린 아직 서로에 대해 몰라! 그러나 우린 그래도 좋아!",
        "너와 나는 역시 반반! 양념반 후라이드반 이후로 최고의 천생연분!",
        "반 이상 맞다니 간신히 과락은 면했습니다. 앞으로의 관계가 더욱 더 기대되는 우리 관계! 앞으로 쭉~ 함께 행복하기!!",
        "럭키 세븐! 이라고 하죠? 두 분이 함께 하면 항상 좋은 일만 생길꺼에요.",
        "8 대 2 완벽한 비율이 아닐까요? 100%보단 지금 이대로 이정도가 딱이야!!",
        "너무 완벽하면 인간미가 없죠. 1개만 차이 나는 것이 오히려 좋아! 우리 지금 딱 좋아!",
        "우린 천생연분! 절대 헤어지지 말고 오래오래 행복합시다.")

    val text2 = Compatibility(
        "상극! 드디어 정반대의 인간을 만났습니다. 싸우거나 서로의 부족한 점 채우거나, 선택은 두 분의 몫!",
        "혹시 대화가 많이 부족하지 않으셨나요? 오늘부터 서로에게 1일 1질문을 하세요.",
        "취향이 달라서 오히려 좋아! 상대방에 대해 오히려 호기심이 생기는 그런 단계입니다.",
        "혹시 상대방이 가끔 이해가 되지 않을 때가 있으신가요? 당연합니다. 우리는 서로 다른 인격체! 취향을 다르지만 서로 존중하며 좋은 인연을 이어가세요.",
        "나머지 60%를 서로에 대한 애정/우정으로 채우면 어떨까요? 상대방의 눈을 바라보며 상대의 감정을 읽어주세요. 분명 감동받을꺼에요.",
        "50%나 같다니 두분은 아직 가능성이 아주 높은 희망적인 인연입니다. 좋은 인연인 두 사람이 서로를 이해하면서 나머지 50%를 채워주세요.",
        "아깝습니다. 두 분의 취향이 아주 조금 차이가 나네요. 하지만 이 또한 취향 차이일뿐 취향존중!! 서로 장점만 바라보기!",
        "혹시 진짜 가족? 거의 가족같은 관계! 이정도면 거의 가족입니다. 은근히 비슷한 취향을 가진 우리!!",
        "서로 대화를 많이 하셨죠? 우리의 관계는 농익어 가는 그런 사이!",
        "아주 아쉽습니다. 마지막 1개의 퍼즐을 서로의 대화로 채워주세요. 그럼 오래 오래 좋은 인연 이어 가실꺼에요.",
        "두 분은 떨어지는 것은 대한민국의 손해! 서로 아껴주는 모습 보기 좋으니 이대로 쭉~~ 평생 좋은 인연을 이어가세요.")

    val texts = listOf(text1, text2)


    val codeMatchingGameItem =
        userSaveGameItem.combine(gameCode.filterNot { it.isEmpty() }) { saveList, inputGameCode ->
            saveList.filter { it.GameCode.toString() == inputGameCode }
        }

    val _startSaveGameValue = MutableStateFlow<UserSaveGame>(UserSaveGame(0, randomUUID(),"", emptyList()))

    val startSaveGameValue = _startSaveGameValue.replayCache

    val matchingGameItemState: StateFlow<List<UserSaveGame>> = codeMatchingGameItem.stateIn(viewModelScope, SharingStarted.Eagerly, startSaveGameValue)


    private val _togetherGameItem = MutableStateFlow<List<GameItem>>(emptyList())

    val togetherGameItem = _togetherGameItem.asStateFlow()

    val _currentTogetherGameItem =  MutableStateFlow<GameItem>(GameItem(randomUUID().toString(), "", "00", "00", 0, ItemKind.FOOD))

    val currentTogetherGameItem = _currentTogetherGameItem.asStateFlow()


    val success = userSaveGameItem.filterNot { it.isEmpty() }.combine(gameCode.filterNot { it.isEmpty() })  { saveList, inputGameCode ->
        saveList.map { it.GameCode.toString() == inputGameCode }.last() }


















    init {

//        _gameItemFlow.value = items

//        loadUserData()

        fetchAllGameItems()

        _matchTextItem.value = texts

        viewModelScope.launch {
            delay(2000)
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
                add(GameItem(gameItem.id, gameItem.makerName, gameItem.firstItem, gameItem.secondItem,  stageResultValue.value , gameItem.itemKind))
            }.toList()
            _usedGameItem.value = items

        }
    }



    fun saveGameItem() {
        viewModelScope.launch {

            val userSaveItem = _userSaveGameItem.value
            val items = userSaveItem.toMutableList().apply {
                add(
                    UserSaveGame(
                        listNumber = listNumber.value,
                        randomUUID(),
                        saveName.value,
                        usedGameItem.value
                    )
                )
            }
            _userSaveGameItem.value = items
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




data class Compatibility(
    val zero: String,
    val one: String,
    val two: String,
    val three: String,
    val four: String,
    val five: String,
    val six: String,
    val seven: String,
    val eight: String,
    val nine: String,
    val ten: String
    )

data class MatchSelect(val listNumber: Int, val firstUserSelect: String, val SecondUserSelect: String, val selectResult: Boolean)

data class UserSaveGame(val listNumber: Int, val GameCode: UUID = randomUUID(), val saveName: String, val gameItems: List<GameItem>)







